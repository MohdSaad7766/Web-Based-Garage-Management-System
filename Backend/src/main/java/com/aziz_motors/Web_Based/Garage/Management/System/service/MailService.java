package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Appointment;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Customer;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Vehicle;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.AppointmentStatus;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.AppointmentResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.CustomerResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.VehicleResponseDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.time.format.DateTimeFormatter;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendAppointmentMail(AppointmentResponseDto appointment, AppointmentStatus status) {

        CustomerResponseDto customer = appointment.getCustomer();

        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            return;
        }

        String htmlText = getHtmlText(appointment, status);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom("no-reply@azizmotors.com");
            helper.setTo(customer.getEmail());
            helper.setSubject(getSubjectByStatus(status));
            helper.setText(htmlText, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            // log only — async-safe
            System.err.println("Email failed for " + customer.getEmail());
        }
    }


    private String getSubjectByStatus(AppointmentStatus status) {
        return switch (status) {
            case PENDING -> "Appointment Request Received";
            case CONFIRMED -> "Appointment Confirmed";
            case RESCHEDULED -> "Appointment Rescheduled";
            case CANCELLED -> "Appointment Cancelled";
            case COMPLETED -> "Appointment Completed";
            default -> "Appointment Notification";
        };
    }

    private String getHtmlText(AppointmentResponseDto appointment, AppointmentStatus status) {

        CustomerResponseDto customer = appointment.getCustomer();
        VehicleResponseDto vehicle = appointment.getVehicle();

        String dateTime = appointment.getDateTime()
                .format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));

        String statusMessage = switch (status) {
            case PENDING ->
                    "Your appointment request is currently under review. " +
                            "You will be notified once it is confirmed.";

            case CONFIRMED ->
                    "Your appointment has been confirmed. " +
                            "Please arrive at least 10 minutes before the scheduled time.";

            case RESCHEDULED ->
                    "Your appointment has been rescheduled. " +
                            "Please review the updated date and time above.";

            case CANCELLED ->
                    "Your appointment has been cancelled. " +
                            "If this was not intended, please contact our support team.";

            case COMPLETED ->
                    "Your appointment has been successfully completed. " +
                            "Thank you for choosing Aziz Motors.";

            default ->
                    "There is an update regarding your appointment.";
        };

        return """
    <html>
    <body style="margin:0; padding:0; background:#f2f2f2; font-family:Segoe UI, Arial, sans-serif;">
      <table width="100%%" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" style="padding:40px 0;">
            
            <table width="600" cellpadding="0" cellspacing="0" style="background:#ffffff;">
              
              <!-- Header -->
              <tr>
                <td style="padding:24px 32px; border-bottom:3px solid #c62828;">
                  <h1 style="margin:0; font-size:20px; color:#1f1f1f;">
                    Aziz Motors
                  </h1>
                  <p style="margin:4px 0 0; font-size:13px; color:#666;">
                    Appointment Notification
                  </p>
                </td>
              </tr>

              <!-- Body -->
              <tr>
                <td style="padding:32px; color:#1f1f1f;">
                  <p style="margin-top:0; font-size:15px;">
                    Hello %s,
                  </p>

                  <p style="font-size:14px; color:#333;">
                    Here are the details of your appointment:
                  </p>

                  <table width="100%%" style="margin:20px 0; font-size:14px;">
                    <tr>
                      <td style="padding:6px 0; color:#666;">Service</td>
                      <td style="padding:6px 0;">%s</td>
                    </tr>
                    <tr>
                      <td style="padding:6px 0; color:#666;">Date & Time</td>
                      <td style="padding:6px 0;">%s</td>
                    </tr>
                    <tr>
                      <td style="padding:6px 0; color:#666;">Vehicle</td>
                      <td style="padding:6px 0;">%s %s (%d)</td>
                    </tr>
                    <tr>
                      <td style="padding:6px 0; color:#666;">Registration No</td>
                      <td style="padding:6px 0;">%s</td>
                    </tr>
                  </table>

                  <p style="font-size:14px; color:#333;">
                    %s
                  </p>
                </td>
              </tr>

              <!-- Footer -->
              <tr>
                <td style="padding:20px 32px; background:#fafafa; font-size:12px; color:#666;">
                  Aziz Motors · Reliable Vehicle Service
                </td>
              </tr>

            </table>

          </td>
        </tr>
      </table>
    </body>
    </html>
    """.formatted(
                customer.getName(),
                appointment.getServiceType(),
                dateTime,
                vehicle.getManufacturerName(),
                vehicle.getModelName(),
                vehicle.getModelYear(),
                vehicle.getRegistrationNumber(),
                statusMessage
        );
    }

}
