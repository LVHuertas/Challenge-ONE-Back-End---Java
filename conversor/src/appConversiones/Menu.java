package appConversiones;

import javax.swing.JOptionPane;

/*
 * La clase Menu() tendrá todas las opciones de apareción del método JOptionPane.
 * Entre ellas, los cuadros de diálogo para escogencia de las conversiones, 
 * para ingresar el valor a convertir y para salida de la aplicación.
 * */

public class Menu {

	public double valor;
	public String texto;
	
	
	public Menu() {
		// Constructo de Menu()
	}
	
	/*
	 * El método MenuEleccionConversion() toma una matriz de cadenas -que son 
	 * las opciones de conversión- para el cuadro de diálogo con las opciones. 
	 * 
	 * Para la aplicación usaremos conversiones de: Divisa, Longitud, Masa,
	 * Velocidad y Tiempo.*/
	
	public void MenuEleccionConversion() {

		System.out.println("Entrada a menu principal.");
		
		String[] conversiones = { "Divisa", "Longitud", "Masa", "Velocidad", "Tiempo", };

		String conversionSeleccionada = (String) JOptionPane.showInputDialog(null,
				"Seleccione una opción de conversión:", "Menu", JOptionPane.PLAIN_MESSAGE, null, conversiones,
				conversiones[0]);
		
		/* 
		 * Si es que no se escoge una opción, se sale del cuadro de diálogo o se le da
		 * 'cancelar', vamos al método MenuSalida(). En caso contrario, vamos a la clase
		 * Conversion(), al método Convertir.*/
		
		if (conversionSeleccionada == null) {
			MenuSalida();
		} else {
			new Conversion().Convertir(conversionSeleccionada);
		}
	}
	
	public boolean MenuValor(String texto) {
		
		System.out.println("Entrada a menu para ingresar valor.");
		
		String textoAMostrar = "Ingresa el valor de la ";
		
		String valor = JOptionPane.showInputDialog(null, 
				textoAMostrar + texto.substring(0,1).toLowerCase() + texto.substring(1), 
				"Entrada",
				JOptionPane.QUESTION_MESSAGE);
		
		this.texto = texto;
		// Verificamos si la entrada es númerica, si no lo es, generamos un mensaje de
		// error.
		
		try {
			this.valor = Double.parseDouble(valor);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public double getValor() {
		return this.valor;
	}
	
	public void MenuError() {
		
		System.out.println("Entrada a menu de error.");
		
		JOptionPane.showMessageDialog(null, 
				"El valor debe ser numérico.", 
				"Error", 
				JOptionPane.ERROR_MESSAGE);
		
		MenuVolver();
	}

	public void MenuVolver() {
		
		System.out.println("Entrada a menu para eleccion.");
		
		String[] options = {"Volver a menú principal", "Escoger " + this.texto.substring(0,1).toLowerCase() + this.texto.substring(1), "Salir", };

		int eleccion = JOptionPane.showOptionDialog(null, 
				"Selecciona una opción",
				"¿Qué hacer?",
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]); 
		
		switch (eleccion) {
		case 0:
			MenuEleccionConversion();
			break;
		case 1:
			new Conversion().Convertir(this.texto);
			break;
		default:
			MenuSalida();
			break;
		}
		
	}
	
	public void Resultado(String textoResultado) {
		
		System.out.println("Entrada a menu resultado.");
		
		JOptionPane.showMessageDialog(null, 
				textoResultado, 
				"Resultado conversion", 
				JOptionPane.INFORMATION_MESSAGE);
		MenuVolver();
	}
	
	public void MenuSalida() {
		
		System.out.println("Entrada a menu de salida.");
		
		JOptionPane.showMessageDialog(null, "Has salido de la aplicación.");
	}

}
