using System.ComponentModel.DataAnnotations;
using Usuarios.Modelo;

public class UsuarioDTO
    {
        [Required]
        public string UserName { get; set; }
        [Required]
        public string NombreCompleto { get; set; }

        [Required]
        [EmailAddress]
        public string Mail { get; set; }

        [Required]
        [Phone]
        public string Telefono { get; set; }

        [Required]
        public string DireccionPostal { get; set; }
        public string? Password { get; set; }
        public string? IdOAuth2 { get; set; }

        public Usuario toEntity()
        {
            Usuario usuario = new Usuario
            {
                Id = this.UserName,
                NombreCompleto = this.NombreCompleto,
                Mail = this.Mail,
                Telefono = this.Telefono,
                DireccionPostal = this.DireccionPostal
            };
            // Verificar si el DTO contiene el atributo Password
            if (this.Password != null)
            {
                usuario.Password = this.Password;
            }

            // Verificar si el DTO contiene el atributo IdOAuth2
            if (this.IdOAuth2 != null)
            {
                usuario.IdOAuth2 = this.IdOAuth2;
            }
            return usuario;
        }
    }