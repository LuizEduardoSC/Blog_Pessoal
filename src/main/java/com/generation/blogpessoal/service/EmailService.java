package com.generation.blogpessoal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void enviarEmailBoasVindas(String destinatario, String nome) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setFrom("blogpessoal@generation.org");
        message.setTo(destinatario);
        message.setSubject("Bem-vindo ao Blog Pessoal!");
        message.setText("Olá, " + nome + "!\n\nSeu cadastro foi realizado com sucesso. \n\nAproveite para compartilhar suas ideias!");

        try {
            mailSender.send(message);
            System.out.println("E-mail de confirmação enviado para: " + destinatario);
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
