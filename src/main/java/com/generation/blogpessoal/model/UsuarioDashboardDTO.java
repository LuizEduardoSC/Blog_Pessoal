package com.generation.blogpessoal.model;

public class UsuarioDashboardDTO {
    
    private Long id;
    private long totalPostagens;
    private long totalComentarios;
    private String temaDestaque;

    public UsuarioDashboardDTO() {}

    public UsuarioDashboardDTO(Long id, long totalPostagens, long totalComentarios, String temaDestaque) {
        this.id = id;
        this.totalPostagens = totalPostagens;
        this.totalComentarios = totalComentarios;
        this.temaDestaque = temaDestaque;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public long getTotalPostagens() { return totalPostagens; }
    public void setTotalPostagens(long totalPostagens) { this.totalPostagens = totalPostagens; }

    public long getTotalComentarios() { return totalComentarios; }
    public void setTotalComentarios(long totalComentarios) { this.totalComentarios = totalComentarios; }

    public String getTemaDestaque() { return temaDestaque; }
    public void setTemaDestaque(String temaDestaque) { this.temaDestaque = temaDestaque; }
}
