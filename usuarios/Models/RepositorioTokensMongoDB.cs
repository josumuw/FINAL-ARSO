using MongoDB.Driver;
using Tokens.Modelo;

namespace Tokens.Repositorio
{
    public class RepositorioTokensMongoDB : IRepositorioTokens
    {
        private readonly IMongoCollection<Token> tokens;

        public RepositorioTokensMongoDB()
        {
            var client = new MongoClient("mongodb+srv://arso45552:KCFxdwqEU1dz1XVC@cluster0.mopqqog.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
            var database = client.GetDatabase("arso");

            tokens = database.GetCollection<Token>("tokens");
        }

        public string Add(Token entity)
        {
            tokens.InsertOne(entity);

            return entity.Id;
        }

        public void Update(Token entity)
        {
            tokens.ReplaceOne(token => token.Id == entity.Id, entity);
        }

        public void Delete(Token entity)
        {
            tokens.DeleteOne(token => token.Id == entity.Id);
        }

        public List<Token> GetAll()
        {
            return tokens.Find(_ => true).ToList();
        }

        public Token GetById(string id)
        {
            return tokens
                .Find(token => token.Id == id)
                .FirstOrDefault();
        }

        public List<string> GetIds()
        {
            var todas =  tokens.Find(_ => true).ToList();

            return todas.Select(p => p.Id).ToList();

        }
        public Token FindActiveTokenByUserId(string idUsuario)
        {
            // Buscar un token activo para el idUsuario en la base de datos
            return tokens.Find(token =>
                token.IdUsuario == idUsuario &&
                token.FechaExpiracion > DateTime.UtcNow
            ).FirstOrDefault();
        }
    }
}