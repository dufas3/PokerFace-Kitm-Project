using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace PokerFace.Migrations
{
    public partial class changedIntToObject : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "SelectedCard",
                table: "Users",
                newName: "SelectedCardId");

            migrationBuilder.CreateIndex(
                name: "IX_Users_SelectedCardId",
                table: "Users",
                column: "SelectedCardId");

            migrationBuilder.AddForeignKey(
                name: "FK_Users_Cards_SelectedCardId",
                table: "Users",
                column: "SelectedCardId",
                principalTable: "Cards",
                principalColumn: "Id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Users_Cards_SelectedCardId",
                table: "Users");

            migrationBuilder.DropIndex(
                name: "IX_Users_SelectedCardId",
                table: "Users");

            migrationBuilder.RenameColumn(
                name: "SelectedCardId",
                table: "Users",
                newName: "SelectedCard");
        }
    }
}
