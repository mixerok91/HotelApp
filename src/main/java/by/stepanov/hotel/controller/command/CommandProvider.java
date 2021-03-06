package by.stepanov.hotel.controller.command;

import by.stepanov.hotel.controller.command.impl.*;
import by.stepanov.hotel.controller.command.impl.admincabinet.*;
import by.stepanov.hotel.controller.command.impl.reservation.*;
import by.stepanov.hotel.controller.command.impl.usercabinet.EditUserData;
import by.stepanov.hotel.controller.command.impl.usercabinet.EditUserDataPage;
import by.stepanov.hotel.controller.command.impl.usercabinet.ShowAllUsersReservations;
import by.stepanov.hotel.controller.command.impl.usercabinet.UserCabinetPage;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private Map<ParameterName, Command> commands = new HashMap<>();

    public CommandProvider(){
        commands.put(ParameterName.REGISTRATION_PAGE, new RegistrationPage());
        commands.put(ParameterName.REGISTRATION, new Registration());
        commands.put(ParameterName.LOGIN_PAGE, new LoginPage());
        commands.put(ParameterName.LOGIN, new Login());
        commands.put(ParameterName.LOGOUT, new Logout());

        commands.put(ParameterName.MAIN_PAGE, new MainPage());

        commands.put(ParameterName.RESERVATION_PAGE, new ReservationPage());
        commands.put(ParameterName.FIND_SUITABLE_ROOMS, new FindSuitableRooms());

        commands.put(ParameterName.RESERVATION_CONFIRM_PAGE, new ReservationConfirmPage());
        commands.put(ParameterName.RESERVATION_CONFIRM, new ReservationConfirm());
        commands.put(ParameterName.RESERVATION_UNDO, new ReservationUndo());

        commands.put(ParameterName.USER_CABINET_PAGE, new UserCabinetPage());
        commands.put(ParameterName.SHOW_ALL_USERS_RESERVATIONS, new ShowAllUsersReservations());
        commands.put(ParameterName.EDIT_USER_DATA_PAGE, new EditUserDataPage());
        commands.put(ParameterName.EDIT_USER_DATA, new EditUserData());

        commands.put(ParameterName.RESERVATION_PAYMENT, new ReservationPayment());
        commands.put(ParameterName.RESERVATION_CANCEL, new ReservationCancel());

        commands.put(ParameterName.ADMIN_CABINET_PAGE, new AdminCabinetPage());
        commands.put(ParameterName.ROOM_TYPE_ADMINISTRATION_PAGE, new RoomTypeAdministrationPage());
        commands.put(ParameterName.ROOM_ADMINISTRATION_PAGE, new RoomAdministrationPage());
        commands.put(ParameterName.GET_RESERVATIONS_BY_PERIOD, new GetReservationsByPeriod());
        commands.put(ParameterName.CREATE_ROOM_TYPE, new CreateRoomType());
        commands.put(ParameterName.EDIT_ROOM_TYPE, new EditRoomType());
        commands.put(ParameterName.CREATE_ROOM, new CreateRoom());
        commands.put(ParameterName.EDIT_ROOM, new EditRoom());
        commands.put(ParameterName.GET_USERS_INFORMATION_BY_EMAIL, new GetUsersReservationsByEmail());

        commands.put(ParameterName.CHANGE_LOCALE, new ChangeLocale());
    }

    public Command getCommand(String commandName){
        Command command;
        ParameterName valueName;

        commandName = commandName.toUpperCase();
        valueName = ParameterName.valueOf(commandName);

        command = commands.get(valueName);

        return command;
    }
}
