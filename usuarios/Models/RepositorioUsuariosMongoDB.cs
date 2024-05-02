using MongoDB.Driver;
using Usuarios.Modelo;

namespace Usuarios.Repositorio
{
    public class RepositorioUsuariosMongoDB : IRepositorioUsuarios
    {
        private readonly IMongoCollection<Usuario> usuarios;

        public RepositorioUsuariosMongoDB()
        {
            var client = new MongoClient("mongodb+srv://arso45552:KCFxdwqEU1dz1XVC@cluster0.mopqqog.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
            var database = client.GetDatabase("arso");

            usuarios = database.GetCollection<Usuario>("usuarios");
        }

        public string Add(Usuario entity)
        {
            usuarios.InsertOne(entity);

            return entity.Id;
        }

        public void Update(Usuario entity)
        {
            usuarios.ReplaceOne(usuario => usuario.Id == entity.Id, entity);
        }

        public void Delete(Usuario entity)
        {
            usuarios.DeleteOne(usuario => usuario.Id == entity.Id);
        }

        public List<Usuario> GetAll()
        {
            return usuarios.Find(_ => true).ToList();
        }

        public Usuario GetById(string id)
        {
            return usuarios
                .Find(usuario => usuario.Id == id)
                .FirstOrDefault();
        }

        public List<string> GetIds()
        {
            var todas =  usuarios.Find(_ => true).ToList();

            return todas.Select(p => p.Id).ToList();

        }
        public Usuario GetByIdOAuth2(string idOAuth2)
        {
            var filter = Builders<Usuario>.Filter.Eq(u => u.IdOAuth2, idOAuth2);
            return usuarios.Find(filter).FirstOrDefault();
        }
    }
}