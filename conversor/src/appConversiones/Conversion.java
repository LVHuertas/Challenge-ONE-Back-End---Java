package appConversiones;

import javax.swing.JOptionPane;

public class Conversion {

	private double valor;

	private Menu menu = new Menu();

	private String textoAMostrar;
	private String[] conversiones;

	private String[] conversionesDivisa = { "Pesos colombianos", "Dolares", "Euros", "Libras esterlinas", "Yen japonés",
			"Won sur-coreano", };

	private String[] conversionesLongitud = { "Centímetros", "Metros", "Kilómetros", };

	private String[] conversionesMasa = { "Gramos", "Kilogramos", };

	private String[] conversionesVelocidad = { "Metros por segundo", "Kilómetros por hora", };

	private String[] conversionesTiempo = { "Segundos", "Minutos", "Horas", };

	public Conversion() {
		//Constructor Conversion()
	}

	public void Convertir(String seleccion) {

		this.textoAMostrar = seleccion;
		System.out.println("Se escogio: " + textoAMostrar + ".");
		
		/*
		 * Respecto a la conversion escogida, se abre un nuevo cuadro de opciones,
		 * por eso se escoge cuál es la matriz de cadenas que se debe mostrar. 
		 */

		switch (textoAMostrar) {
		case "Divisa":
			this.conversiones = conversionesDivisa;
			break;
		case "Longitud":
			this.conversiones = conversionesLongitud;
			break;
		case "Masa":
			this.conversiones = conversionesMasa;
			break;
		case "Velocidad":
			this.conversiones = conversionesVelocidad;
			break;
		case "Tiempo":
			this.conversiones = conversionesTiempo;
			break;
		}
		
		String textoAMostrarMinuscula = textoAMostrar.substring(0, 1).toLowerCase() + textoAMostrar.substring(1);

		String aConvertir = (String) JOptionPane.showInputDialog(null,
				"Seleccione la " + textoAMostrarMinuscula + " a convertir:", textoAMostrar, JOptionPane.PLAIN_MESSAGE,
				null, this.conversiones, this.conversiones[0]);
		
		/*
		 * Después de escoger la opción 'a convertir', se crea un nuevo arreglo con 
		 * un elemento menos de las opciones elegidas. Entonces, agregamos las opciones en el arreglo, pero
		 * sin la opción elegida; esta sería la opción 'convertir a'. 
		 * 
		 * En caso de que aparezca algún error, se lo atrapa con la setencia 'try... catch'. Estos errores
		 * provocarán que se salga de la aplicación, ya que se presentan cuando no se escoge opción o
		 * se le da al botón 'cancelar'.
		 * 
		 * En caso de que sea exitoso, vamos a los métodos de conversion.*/
		
		String[] nuevoConversiones = new String[conversiones.length - 1];	
		
		try {
			for (int i = 0, k = 0; i < conversiones.length; i++) {
				if (conversiones[i] == aConvertir) {
					continue;
				}
				nuevoConversiones[k++] = conversiones[i];
			}
			
			String convertirA = (String) JOptionPane.showInputDialog(null,
					"Seleccione a qué " + textoAMostrarMinuscula + " desea convertir:", 
					textoAMostrar,
					JOptionPane.PLAIN_MESSAGE, 
					null, 
					nuevoConversiones, 
					nuevoConversiones[0]);
			
			if (convertirA == null) {
				menu.MenuSalida();
			} else {
				boolean esNumero = menu.MenuValor(textoAMostrar);
				
				if (esNumero == true) {
					double valor = menu.getValor();
					System.out.println("Se convertiran de " + aConvertir + " a " + convertirA);
					ResultadoConversion(aConvertir, convertirA, valor);
				} else {
					menu.MenuError();
				}
			}
			
		} catch(ArrayIndexOutOfBoundsException | NullPointerException e) {			
			menu.MenuSalida();			
		}
	}
	
	/*
	 * Respecto a la opción de conversión, se entra a cada caso y se convierte el valor ingresado.
	 * Después, se presenta el resultado en un cuadro de diálogo. 
	 * 
	 * Se sigue con un el menu de elección, donde se puede elegir opciones, elegir conversiones de la opción 
	 * ya elegida o se puede salir de la aplicación.
	 * 
	*/ 
	
	private void ResultadoConversion(String aConvertir, String convertirA, double valor) {

		switch (textoAMostrar) {
		case "Divisa":
			this.valor = EscogerDivisa(valor, aConvertir, convertirA);
			break;
		case "Longitud":
			this.valor = EscogerLongitud(valor, aConvertir, convertirA);
			break;
		case "Masa":
			this.valor = EscogerMasa(valor, aConvertir, convertirA);
			break;
		case "Velocidad":
			this.valor = EscogerVelocidad(valor, aConvertir, convertirA);
			break;
		case "Tiempo":
			this.valor = EscogerTiempo(valor, aConvertir, convertirA);
			break;
		}
		
		String resultadoConversion = valor + " " + aConvertir.substring(0, 1).toLowerCase() + aConvertir.substring(1)
				+ " son " + this.valor + " " + convertirA.substring(0, 1).toLowerCase() + convertirA.substring(1);
		
		menu.Resultado(resultadoConversion);
	}
	
	/*
	 * Todas las opciones de 'Escoger...' toman la tasa de conversión y respecto a las opciones elegidas,
	 * se haya el valor de conversión del valor numérico ingresado.
	*/

	private double EscogerDivisa(double valor, String aConvertir, String convertirA) {
		
		/*
		 * Referencias del 28/02/23
		 * 
		 * Recordar que las conversiones inversas solo son 1 sobre el valor.
		 * Sin embargo, la mayoría de conversiones de monedas usan valores redondeados,
		 * por lo que es mejor usar la tasa exacta.
		 * 
		*/

		// Conversiones de divisas
		double tasaConversionPesoAEuro = 0.0002;
		double tasaConversionPesoADolar = 0.00021;
		double tasaConversionPesoALibra = 0.00017;
		double tasaConversionPesoAYen = 0.029;
		double tasaConversionPesoAWon = 0.28;

		double tasaConversionDolarAPeso = 4809.75;
		double tasaConversionDolarAEuro = 0.95;
		double tasaConversionDolarALibra = 0.83;
		double tasaConversionDolarAYen = 136.34;
		double tasaConversionDolarAWon = 1323.07;
		
		//Única referencia del 2/03/23
		double tasaConversionEuroAPeso = 5123;
		double tasaConversionEuroADolar = 1.06;
		double tasaConversionEuroALibra = 0.89;
		double tasaConversionEuroAYen = 144.90;
		double tasaConversionEuroAWon = 1385.48;

		double tasaConversionLibraAPeso = 5782.59;
		double tasaConversionLibraADolar = 1.2;
		double tasaConversionLibraAEuro = 1.14;
		double tasaConversionLibraAYen = 163.94;
		double tasaConversionLibraAWon = 1590.63;

		double tasaConversionYenAPeso = 35.26;
		double tasaConversionYenADolar = 0.0073;
		double tasaConversionYenAEuro = 0.0069;
		double tasaConversionYenALibra = 0.0061;
		double tasaConversionYenAWon = 9.70;

		double tasaConversionWonAPeso = 3.64;
		double tasaConversionWonADolar = 0.00076;
		double tasaConversionWonAEuro = 0.00072;
		double tasaConversionWonALibra = 0.00063;
		double tasaConversionWonAYen = 0.10;
		
		double valorDivisa = 0;

		switch (aConvertir) {
		case "Pesos colombianos":
			switch (convertirA) {
			case "Dolares":
				valorDivisa = valor * tasaConversionPesoADolar;
				break;
			case "Euros":
				valorDivisa = valor * tasaConversionPesoAEuro;
				break;
			case "Libras esterlinas":
				valorDivisa = valor * tasaConversionPesoALibra;
				break;
			case "Yen japonés":
				valorDivisa = valor * tasaConversionPesoAYen;
				break;
			case "Won sur-coreano":
				valorDivisa = valor * tasaConversionPesoAWon;
				break;
			}
			break;
		case "Dolares":
			switch (convertirA) {
			case "Pesos colombianos":
				valorDivisa = valor * tasaConversionDolarAPeso;
				break;
			case "Euros":
				valorDivisa = valor * tasaConversionDolarAEuro;
				break;
			case "Libras esterlinas":
				valorDivisa = valor * tasaConversionDolarALibra;
				break;
			case "Yen japonés":
				valorDivisa = valor * tasaConversionDolarAYen;
				break;
			case "Won sur-coreano":
				valorDivisa = valor * tasaConversionDolarAWon;
				break;
			}
			break;
		case "Euros":
			switch (convertirA) {
			case "Pesos colombianos":
				valorDivisa = valor * tasaConversionEuroAPeso;
				break;
			case "Dolares":
				valorDivisa = valor * tasaConversionEuroADolar;
				break;
			case "Libras esterlinas":
				valorDivisa = valor * tasaConversionEuroALibra;
				break;
			case "Yen japonés":
				valorDivisa = valor * tasaConversionEuroAYen;
				break;
			case "Won sur-coreano":
				valorDivisa = valor * tasaConversionEuroAWon;
				break;
			}
			break;
		case "Libras esterlinas":
			switch (convertirA) {
			case "Pesos colombianos":
				valorDivisa = valor * tasaConversionLibraAPeso;
				break;
			case "Dolares":
				valorDivisa = valor * tasaConversionLibraADolar;
				break;
			case "Euros":
				valorDivisa = valor * tasaConversionLibraAEuro;
				break;
			case "Yen japonés":
				valorDivisa = valor * tasaConversionLibraAYen;
				break;
			case "Won sur-coreano":
				valorDivisa = valor * tasaConversionLibraAWon;
				break;
			}
			break;
		case "Yen japonés":
			switch (convertirA) {
			case "Pesos colombianos":
				valorDivisa = valor * tasaConversionYenAPeso;
				break;
			case "Dolares":
				valorDivisa = valor * tasaConversionYenADolar;
				break;
			case "Euros":
				valorDivisa = valor * tasaConversionYenAEuro;
				break;
			case "Libras esterlinas":
				valorDivisa = valor * tasaConversionYenALibra;
				break;
			case "Won sur-coreano":
				valorDivisa = valor * tasaConversionYenAWon;
				break;
			}
			break;
		case "Won sur-coreano":
			switch (convertirA) {
			case "Pesos colombianos":
				valorDivisa = valor * tasaConversionWonAPeso;
				break;
			case "Dolares":
				valorDivisa = valor * tasaConversionWonADolar;
				break;
			case "Euros":
				valorDivisa = valor * tasaConversionWonAEuro;
				break;
			case "Libras esterlinas":
				valorDivisa = valor * tasaConversionWonALibra;
				break;
			case "Yen japonés":
				valorDivisa = valor * tasaConversionWonAYen;
				break;
			}
			break;
		}

		return valorDivisa;
	}

	private double EscogerLongitud(double valor, String aConvertir, String convertirA) {
		
		// Conversiones de longitud

		double tasaConversionMetrosACentrimetros = 100;
		double tasaConversionKilometrosACentimetros = 100000;
		double tasaConversionKilometrosAMetros = 1000;
		
		double valorLongitud = 0;

		switch (aConvertir) {
		case "Centímetros":
			switch (convertirA) {
			case "Metros":
				valorLongitud = valor * (1 / tasaConversionMetrosACentrimetros);
				break;
			case "Kilómetros":
				valorLongitud = valor * (1 / tasaConversionKilometrosACentimetros);
				break;
			}
			break;
		case "Metros":
			switch (convertirA) {
			case "Centímetros":
				valorLongitud = valor * tasaConversionMetrosACentrimetros;
				break;
			case "Kilómetros":
				valorLongitud = valor * (1 / tasaConversionKilometrosAMetros);
				break;
			}
			break;
		case "Kilómetros":
			switch (convertirA) {
			case "Centímetros":
				valorLongitud = valor * tasaConversionKilometrosACentimetros;
				break;
			case "Metros":
				valorLongitud = valor * tasaConversionKilometrosAMetros;
				break;
			}
			break;
		}

		System.out.println(valorLongitud);
		return valorLongitud;
	}

	private double EscogerMasa(double valor, String aConvertir, String convertirA) {

		// Conversiones de masa

		double tasaConversionKilogramosAGramos = 1000;
		
		double valorMasa = 0;
		
		switch (aConvertir) {
		case "Gramos":
			switch (convertirA) {
			case "Kilogramos":
				valorMasa = valor * (1 / tasaConversionKilogramosAGramos);
				break;
			}
		case "Kilogramos":
			switch (convertirA) {
			case "Gramos":
				valorMasa = valor * tasaConversionKilogramosAGramos;
				break;
			}
		}

		return valorMasa;
	}

	private double EscogerVelocidad(double valor, String aConvertir, String convertirA) {


		// Conversiones de velocidad

		double tasaConversionMsAKh = 3.6;
		
		double valorVelocidad = 0;
		
		switch (aConvertir) {
		case "Metros por segundo":
			switch (convertirA) {
			case "Kilómetros por hora":
				valorVelocidad = valor * tasaConversionMsAKh;
				break;
			}
			break;
		case  "Kilómetros por hora":
			switch (convertirA) {
			case "Metros por segundo":
				valorVelocidad = valor * (1 / tasaConversionMsAKh);
				break;
			}
			break;
		}

		return valorVelocidad;
	}

	private double EscogerTiempo(double valor, String aConvertir, String convertirA) {
		
		// Conversiones de tiempo

		double tasaConversionMinutosASegundos = 60;
		double tasaConversionHorasASegundos = 3600;
		
		double valorTiempo = 0;
		
		switch (aConvertir) {
		case "Segundos":
			switch (convertirA) {
			case "Minutos":
				valorTiempo = valor * (1 / tasaConversionMinutosASegundos);
				break;
			case "Horas":
				valorTiempo = valor * (1 / tasaConversionHorasASegundos); 
				break;
			}
			break;
		case "Minutos":
			switch (convertirA) {
			case "Segundos":
				valorTiempo = valor * tasaConversionMinutosASegundos;
				break;
			case "Horas":
				valorTiempo = valor * (1 / tasaConversionMinutosASegundos);
				break;
			}
			break;
		case "Horas":
			switch (convertirA) {
			case "Segundos":
				valorTiempo = valor * tasaConversionHorasASegundos;
				break;
			case "Minutos":
				valorTiempo = valor * tasaConversionMinutosASegundos;
				break;
			}
			break;
		}

		return valorTiempo;
	}

}
