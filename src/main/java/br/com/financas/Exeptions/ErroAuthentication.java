package br.com.financas.Exeptions;

public class ErroAuthentication extends RuntimeException{

	public ErroAuthentication(String Msg) {
		super(Msg);
	}
}
