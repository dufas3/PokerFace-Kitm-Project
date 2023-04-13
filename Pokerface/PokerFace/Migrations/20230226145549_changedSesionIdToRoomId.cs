using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace PokerFace.Migrations
{
    public partial class changedSesionIdToRoomId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "SessionId",
                table: "Users");

            migrationBuilder.AddColumn<int>(
                name: "RoomId",
                table: "Users",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "RoomId",
                table: "Sessions",
                type: "int",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "RoomId",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "RoomId",
                table: "Sessions");

            migrationBuilder.AddColumn<int>(
                name: "SessionId",
                table: "Users",
                type: "int",
                nullable: false,
                defaultValue: 0);
        }
    }
}
