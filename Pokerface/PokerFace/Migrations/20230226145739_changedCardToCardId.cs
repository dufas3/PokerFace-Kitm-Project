using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace PokerFace.Migrations
{
    public partial class changedCardToCardId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Users_Card_SelectCardId",
                table: "Users");

            migrationBuilder.DropTable(
                name: "Card");

            migrationBuilder.DropIndex(
                name: "IX_Users_SelectCardId",
                table: "Users");

            migrationBuilder.RenameColumn(
                name: "SelectCardId",
                table: "Users",
                newName: "SelectedCard");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "SelectedCard",
                table: "Users",
                newName: "SelectCardId");

            migrationBuilder.CreateTable(
                name: "Card",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    IsActive = table.Column<int>(type: "int", nullable: false),
                    Value = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Card", x => x.Id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Users_SelectCardId",
                table: "Users",
                column: "SelectCardId");

            migrationBuilder.AddForeignKey(
                name: "FK_Users_Card_SelectCardId",
                table: "Users",
                column: "SelectCardId",
                principalTable: "Card",
                principalColumn: "Id");
        }
    }
}
