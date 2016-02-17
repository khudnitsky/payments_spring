/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.factory;

import by.pvt.khudnitsky.payments.commands.ICommand;
import by.pvt.khudnitsky.payments.commands.impl.admin.GoBackAdminCommand;
import by.pvt.khudnitsky.payments.commands.impl.admin.GoToUnblockCommand;
import by.pvt.khudnitsky.payments.commands.impl.admin.ShowClientsCommand;
import by.pvt.khudnitsky.payments.commands.impl.admin.ShowOperationsCommand;
import by.pvt.khudnitsky.payments.commands.impl.admin.UnblockCommand;
import by.pvt.khudnitsky.payments.commands.impl.client.AddFundsCommand;
import by.pvt.khudnitsky.payments.commands.impl.client.BalanceCommand;
import by.pvt.khudnitsky.payments.commands.impl.client.BlockCommand;
import by.pvt.khudnitsky.payments.commands.impl.client.GoBackClientCommand;
import by.pvt.khudnitsky.payments.commands.impl.client.GoToAddFundsCommand;
import by.pvt.khudnitsky.payments.commands.impl.client.GoToPaymentCommand;
import by.pvt.khudnitsky.payments.commands.impl.client.PaymentCommand;
import by.pvt.khudnitsky.payments.commands.impl.user.GoBackCommand;
import by.pvt.khudnitsky.payments.commands.impl.user.GoToRegistrationCommand;
import by.pvt.khudnitsky.payments.commands.impl.user.LoginUserCommand;
import by.pvt.khudnitsky.payments.commands.impl.user.LogoutUserCommand;
import by.pvt.khudnitsky.payments.commands.impl.user.RegistrationCommand;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public enum CommandType {
    //user commands
    LOGIN, LOGOUT, REGISTRATION, GOTOREGISTRATION, BACK,

    // client commands
    PAYMENT, GOTOPAYMENT, BALANCE, ADDFUNDS, GOTOADDFUNDS, BLOCK, BACKCLIENT,

    // admin commands
    CLIENTS, OPERATIONS, UNBLOCK, GOTOUNBLOCK, BACKADMIN;

    public ICommand getCurrentCommand() throws EnumConstantNotPresentException{
        switch(this){
            case LOGIN:
                return new LoginUserCommand();

            case LOGOUT:
                return new LogoutUserCommand();

            case REGISTRATION:
                return new RegistrationCommand();

            case GOTOREGISTRATION:
                return new GoToRegistrationCommand();

            case BACK:
                return new GoBackCommand();

            case PAYMENT:
                return new PaymentCommand();

            case GOTOPAYMENT:
                return new GoToPaymentCommand();

            case BALANCE:
                return new BalanceCommand();

            case ADDFUNDS:
                return new AddFundsCommand();

            case GOTOADDFUNDS:
                return new GoToAddFundsCommand();

            case BLOCK:
                return new BlockCommand();

            case BACKCLIENT:
                return new GoBackClientCommand();

            case CLIENTS:
                return new ShowClientsCommand();

            case OPERATIONS:
                return new ShowOperationsCommand();

            case UNBLOCK:
                return new UnblockCommand();

            case GOTOUNBLOCK:
                return new GoToUnblockCommand();

            case BACKADMIN:
                return new GoBackAdminCommand();

            default:
                return new LoginUserCommand();
                //throw new EnumConstantNotPresentException(this.getDeclaringClass(), this.name());
        }
    }

    public String getValue(){
        switch(this){
            case PAYMENT:
                return "Платеж";

            case BLOCK:
                return "Блокировка счета";

            case UNBLOCK:
                return "Разблокировка счета";

            case ADDFUNDS:
                return "Пополнение счета";

            default:
                throw new EnumConstantNotPresentException(this.getDeclaringClass(), this.name());
        }
    }
}
