using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace PokerFace.Migrations
{
    public partial class activeDataSave : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "SessionId",
                table: "Settings",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "SessionId",
                table: "Cards",
                type: "int",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Settings_SessionId",
                table: "Settings",
                column: "SessionId");

            migrationBuilder.CreateIndex(
                name: "IX_Cards_SessionId",
                table: "Cards",
                column: "SessionId");

            migrationBuilder.AddForeignKey(
                name: "FK_Cards_Sessions_SessionId",
                table: "Cards",
                column: "SessionId",
                principalTable: "Sessions",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Settings_Sessions_SessionId",
                table: "Settings",
                column: "SessionId",
                principalTable: "Sessions",
                principalColumn: "Id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Cards_Sessions_SessionId",
                table: "Cards");

            migrationBuilder.DropForeignKey(
                name: "FK_Settings_Sessions_SessionId",
                table: "Settings");

            migrationBuilder.DropIndex(
                name: "IX_Settings_SessionId",
                table: "Settings");

            migrationBuilder.DropIndex(
                name: "IX_Cards_SessionId",
                table: "Cards");

            migrationBuilder.DropColumn(
                name: "SessionId",
                table: "Settings");

            migrationBuilder.DropColumn(
                name: "SessionId",
                table: "Cards");
        }
    }
}
