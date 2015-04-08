package com.doremitutor.support;

import java.util.HashMap;

import com.doremitutor.support.Texts.Lang;
import com.doremitutor.support.Texts.Text;

public class Texts extends HashMap<Text, HashMap<Lang, String>>{
	public static String TITLE_TRAILER=" - www.doremitutor.com";
	public static enum Lang{ES, EN};
	public static enum Text{
		/* Support */
		SITE_BUTTON, OFFLINE_NOTIFICATION, NO_MIDI_NOTIFICATION, NO_MIDI_WARNING_TITLE,
		/* Sound */
		SOUND_WAVES_TITLE, SOUND_WAVES_LINES, KEEP_BUTTON_DOWN_TO_ANIMATE, AIR_COMPRESSION, 
		EVERY_SEC, EVERY_TWO_SEC, EVERY_FOUR_SEC, SOUND_SAMPLES_TITLE, KEEP_ONE_BUTTON_DOWN_TO_LISTEN,
		CONSONANCE_TITLE, CENTI_SECOND, MOVE_SENOIDS, RESET_POSITIONS,
		/* Time */
		PULSES_TITLE, PULSES, KEEP_THE_BUTTON_DOWN_TO_LISTEN, PULSES_PER_MINUTE,
		BAR_GENERATOR_TITLE, PULSES_PER_BAR,
		/* Solfege lessons */
		LESSON_TITLE_LEADER, FLUTE, CLARINET, VIOLIN, ORGAN, INITIAL_BAR, SHOW_NOTE_NAMES,
		ENHANCE_PLAYING_BAR, START_STOP, TUNE_UP, REWIND, GO_TO_BAR, STATE, PRECOUNTING,
		STANDING_BY, PLAYING, PAUSED, TUNING_UP, TUNE, TUNE_TIP, RW_TIP, START_STOP_TIP, VOLUME,
		/* Note hunting */
		NOTE_HUNTING_TITLE_TREBLE, NOTE_HUNTING_TITLE_BASS, NOTE_PRESENTATION, RESET,
		HUNTING_BUTTONS, CONTROL_BUTTONS, UPPER_RANGE, MID_RANGE, LOWER_RANGE, ALL_RANGES, CHOOSE_RANGE,
		SCORE, STATUS, NEGATIVE_SCORE, HELP, HUNTING_START_STOP_DEFAULT,
		PAUSE_HUNTING, RESUME_HUNTING, HUNTING_PAUSED, HUNTING_ON, CLOSE_HELP, GENERAL_INFO		
	}
	/*private static Lang LANG;
	public static void setLang(String lang){
		LANG=Lang.valueOf(lang.toUpperCase());
	}*/
	public static Lang getLang(String langString){
		return Lang.valueOf(langString.toUpperCase());
	}
	
	private static Texts map=new Texts();
	public static String getText(Text text, Lang lang){
		return map.get(text).get(lang);
	}
	private Texts(){
		assert map==null:"Singleton case";
		/*put(, "", "");*/
		/* Support */
		put(Text.SITE_BUTTON, "Ir al sitio web", "Go to the site");
		put(Text.OFFLINE_NOTIFICATION, "Esta aplicaci�n s�lo puede ejecutarse en l�nea",
				"This application can only be executed on-line");
		put(Text.NO_MIDI_NOTIFICATION,
				"Advertencia:-El sintetizador Java no se encuentra.-Quiz�s necesite reinstalar Java y/o-verificar su configuraci�n de sonido.",
				"Warning:-Java synthesizer not found.-Perharps you need to reinstall Java and/or-verify your sound configuration.");
		put(Text.NO_MIDI_WARNING_TITLE, "Advertencia: Sintetizador no encontrado", "Warning: Synthesizer not found");
		/* Sound */
		put(Text.SOUND_WAVES_TITLE, "Demostraci�n de las ondas sonoras", "Sound waves demonstration");
		put(Text.SOUND_WAVES_LINES, "Elemento vibrante en rojo; aire en oscilaci�n pero estacionario, l�neas delgadas",
				"Vibrating element in red; oscillating but stationary air, thin lines");
		put(Text.KEEP_BUTTON_DOWN_TO_ANIMATE, "Mantener un bot�n presionado para animar ilustraci�n",
				"Keep button pressed to animate illustration");
		put(Text.AIR_COMPRESSION, "Gr�fica de presi�n del aire", "Air pressure graphic");
		put(Text.EVERY_SEC, "Una oscilaci�n por segundo", "One oscillation every second");
		put(Text.EVERY_TWO_SEC, "Una oscilaci�n en dos segundos", "One oscillation every two seconds");
		put(Text.EVERY_FOUR_SEC, "Una oscilaci�n en cuatro segundos", "One oscillation every four seconds");
		put(Text.SOUND_SAMPLES_TITLE, "Muestras de sonidos", "Sound samples");
		put(Text.KEEP_ONE_BUTTON_DOWN_TO_LISTEN, "Mantener un bot�n presionado para escuchar el audio",
				"Listen to the audio by keeping one button pressed");
		put(Text.CONSONANCE_TITLE, "Demostraci�n de consonancia", "Consonance demonstration");
		put(Text.CENTI_SECOND, "Una cent�sima de segundo (0.01 seg)", "One hundredth of a second (0.01 sec)");
		put(Text.MOVE_SENOIDS, "Mueva las senoides verticalmente arrastrando su bot�n",
				"Move senoids vertically by dragging their button");
		put(Text.RESET_POSITIONS, "Restablecer posiciones", "Reset positions");
		/* Time */
		put(Text.PULSES_TITLE, "Generador de pulsos", "Pulse generator");
		put(Text.PULSES, "Pulsos", "Pulses");
		put(Text.KEEP_THE_BUTTON_DOWN_TO_LISTEN, "Mantener el bot�n presionado para escuchar el audio",
				"Listen to the audio by keeping the button pressed");
		put(Text.PULSES_PER_MINUTE, "Tiempos por minuto", "Beats per minute");
		put(Text.BAR_GENERATOR_TITLE, "Generador de compases", "Bar generator");
		put(Text.PULSES_PER_BAR, "Tiempos por comp�s", "Beats per bar");
		/* Solfege lessons */
		put(Text.LESSON_TITLE_LEADER, "Lecci�n de solfeo \"", "Solf�ge lesson \"");
		put(Text.FLUTE, "Flauta", "Flute");
		put(Text.CLARINET, "Clarinete", "Clarinet");
		put(Text.VIOLIN, "Viol�n", "Violin");
		put(Text.ORGAN, "�rgano", "Organ");
		put(Text.INITIAL_BAR, "Marcar comp�s inicial de conteo", "Play initial counting measure");
		put(Text.SHOW_NOTE_NAMES, "Mostrar nombres de notas", "Show note names");
		put(Text.ENHANCE_PLAYING_BAR, "Resaltar comp�s en ejecuci�n", "Enhance bar in execution");
		put(Text.START_STOP, "* INICIAR/DETENER: Alt+P", "* START/STOP: Alt+P");
		put(Text.TUNE_UP, "* ENTONACI�N: Alt+T", "* TUNE UP: Alt+T");
		put(Text.REWIND, "* REGRESAR: Alt+R", "* REWIND: Alt+R");
		put(Text.GO_TO_BAR, "* PARA IR A UN COMP�S: Click en �l", "* TO GO TO A BAR: Click on it");
		put(Text.STATE, "Estado", "State");
		put(Text.PRECOUNTING, "En preconteo", "Precounting");
		put(Text.STANDING_BY, "En espera", "Standing by");
		put(Text.PLAYING, "Ejecutando", "Playing");
		put(Text.PAUSED, "En Pausa", "Paused");
		put(Text.TUNING_UP, "Entonando", "Tuning up");
		put(Text.TUNE, "Entonar", "Tune up");
		put(Text.TUNE_TIP, "Alt-T para entonarse", "Alt-T to tune up");
		put(Text.RW_TIP, "Alt-R para regresar", "Alt-R to rewind");
		put(Text.START_STOP_TIP, "Alt-P para arrancar/detener", "Alt-P to start/stop");
		put(Text.VOLUME, "Volumen", "Volume");
		/* Note hunting */
		put(Text.NOTE_HUNTING_TITLE_TREBLE, "Ejercitador de reconocimiento visual de notas en clave de Sol",
				"Treble clef note visual recognition exerciser");
		put(Text.NOTE_HUNTING_TITLE_BASS, "Ejercitador de reconocimiento visual de notas en clave de Fa",
				"Bass clef note visual recognition exerciser");
		put(Text.NOTE_PRESENTATION, "Presentaci�n de las notas. Elija un rango e inicie.",
				"Note presentation. Choose a range and start playing.");
		put(Text.RESET, "Reiniciar", "Reset");
		put(Text.HUNTING_BUTTONS, "Botones de caza (cazar con el teclado acumula el doble)",
				"Hunting buttons (keyboard hunting scores twice as much)");
		put(Text.CONTROL_BUTTONS, "Botones de control", "Control buttons");
		put(Text.UPPER_RANGE, "Arriba de la segunda l�nea", "Above the second line");
		put(Text.MID_RANGE, "S�lo en el pentagrama", "Only on the staff");
		put(Text.LOWER_RANGE, "Abajo de la segunda linea", "Below the second line");
		put(Text.ALL_RANGES, "Todos", "All");
		put(Text.CHOOSE_RANGE, "Seleccione rango", "Choose a range");
		put(Text.SCORE, "Puntuaci�n", "Score");
		put(Text.STATUS, "Estado", "Status");
		put(Text.NEGATIVE_SCORE, "Con puntuaci�n negativa, debe volver a empezar. Oprima Reiniciar.",
				"With a negative score, you must start over. Click Reset.");
		put(Text.HELP, "Ayuda", "Help");
		put(Text.HUNTING_START_STOP_DEFAULT, "Iniciar", "Start");
		put(Text.PAUSE_HUNTING, "Suspender", "Pause");
		put(Text.RESUME_HUNTING, "Reanudar", "Resume");
		put(Text.HUNTING_PAUSED, "Sesi�n suspendida", "Session in pause");
		put(Text.HUNTING_ON, "En sesi�n, cada pausa deduce 100 puntos", "Session is on, each pause deduce 100 points");
		put(Text.CLOSE_HELP, "Cerrar ayuda", "Close help");
		put(Text.GENERAL_INFO, "Informaci�n general", "General information");
	}
	private void put(Text text, final String textEs, final String textEn){
		put(text, new HashMap<Lang, String>(){
			{
				put(Lang.ES, textEs);
				put(Lang.EN, textEn);
			}
		});
	}
}