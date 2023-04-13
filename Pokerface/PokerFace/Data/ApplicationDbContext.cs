using Microsoft.EntityFrameworkCore;
using PokerFace.Data.Entities;

namespace PokerFace.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Session>()
                .HasKey(s => s.Id);
        }

        public DbSet<Moderator> Moderators { get; set; }
        public DbSet<Session> Sessions { get; set; }
        public DbSet<Card> Cards { get; set; }
        public DbSet<Setting> Settings { get; set; }
    }
}