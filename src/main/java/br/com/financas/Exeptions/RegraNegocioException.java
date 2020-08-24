package br.com.financas.Exeptions;

public class RegraNegocioException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RegraNegocioException(String Mensage) {
		super(Mensage);
	}

}
