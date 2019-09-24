package com.company.chat.server.command;

import com.company.chat.client.entity.ClientCommandRequest;
import com.company.chat.client.entity.ClientCommandResponse;
import com.company.chat.common.ClientInfo;
import com.company.chat.common.IChatServer;
import com.company.chat.common.ICommand;

public class SimpleCalcCommand  implements ICommand {
	private IChatServer server;
	
	@Override
	public String getCommandName() {
		return "/calc";
	}

	@Override
	public String getDescription() {
		return "Калькулятор с действиями: + - * /";
	}

	@Override
	public void init(IChatServer server) {
		this.server = server;
		
	}

	@Override
	public ClientCommandResponse execute(ClientCommandRequest clientData, long clientId) {		
		String clientName = server.getClientInfoById(clientId).orElse(ClientInfo.getEmpty()).getName();
		if(clientData.getParam().length() < 4)
			return new ClientCommandResponse(false, clientName, "Не верные параметры команды!!!");
		String str = clientData.getParam().trim().replaceAll(" ", "");
		String[] arr = str.split("[+-/*]");
		if(arr.length != 2)
			return new ClientCommandResponse(false, clientName, "Не верные параметры команды!!!");
		for(String item : arr) {
			if(item.isBlank())
				return new ClientCommandResponse(false, clientName, "Не верные параметры команды!!!");
		}
		
		String operator = str.substring(arr[0].length(), arr[0].length() + 1);
		
		double num1 = Double.parseDouble(arr[0]);
		double num2 = Double.parseDouble(arr[1]);
		double res;
		
		String result = clientData.getParam() + " = ";
		
		switch (operator) {
			case "+":
				res = num1 + num2; 
				break;
			case "-":
				res = num1 - num2; 
				break;
			case "*":
				res = num1 * num2; 
				break;
			case "/":
				if(num2 == 0d)
					return new ClientCommandResponse(false, clientName, "Не верные параметры команды!!!");
				res = num1 / num2; 
				break;
			default:
				return new ClientCommandResponse(false, clientName, "Не верные параметры команды!!!");
		}
		return new ClientCommandResponse(true, clientName, result + res);
	}

}
