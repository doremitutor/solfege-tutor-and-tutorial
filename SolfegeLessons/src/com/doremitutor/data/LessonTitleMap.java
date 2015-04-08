package com.doremitutor.data;

import java.util.HashMap;

import com.doremitutor.support.Texts.Lang;

public class LessonTitleMap extends HashMap<String, HashMap<Lang, String>>{
	private static LessonTitleMap map=new LessonTitleMap();
	public static String getTitle(String lessonName, Lang lang){
		return map.get(lessonName).get(lang);
	}
	private LessonTitleMap(){
		assert map==null:"Singleton case";
		put("tonic24-4",				"La tónica en dos por cuatro",					"The tonic in two by four");
		put("tonic34-4",				"La tónica en tres por cuatro",					"The tonic in three by four");
		put("tonic44-4",				"La tónica en cuatro por cuatro",				"The tonic in four by four");
		put("supertonic24-4",			"La supertónica en dos por cuatro",				"The supertonic in two by four");
		put("supertonic34-4",			"La supertónica en tres por cuatro",			"The supertonic in three by four");
		put("supertonic44-4",			"La supertónica en cuatro por cuatro",			"The supertonic in four by four");
		put("uppertonic24-4",			"La tónica superior en dos por cuatro",			"The upper tonic in two by four");
		put("uppertonic34-4",			"La tónica superior en tres por cuatro",		"The upper tonic in three by four");
		put("uppertonic44-4",			"La tónica superior en cuatro por cuatro",		"The upper tonic in four by four");
		put("upperleading24-4",			"La sensible en dos por cuatro",				"The leading tone in two by four");
		put("upperleading34-4",			"La sensible en tres por cuatro",				"The leading tone in three by four");
		put("upperleading44-4",			"La sensible en cuatro por cuatro",				"The leading tone in four by four");
		put("lowerleading24-4",			"La sensible inferior en dos por cuatro",		"The lower leading tone in two by four");
		put("lowerleading34-4",			"La sensible inferior en tres por cuatro",		"The lower leading tone in three by four");
		put("lowerleading44-4",			"La sensible inferior en cuatro por cuatro",	"The lower leading tone in four by four");
		put("dominant-disjunct24-4",	"La dominante en dos por cuatro (1)",			"The dominant in two by four (1)");
		put("dominant-disjunct34-4",	"La dominante en tres por cuatro (1)",			"The dominant in three by four (1)");
		put("dominant-disjunct44-4",	"La dominante en cuatro por cuatro (1)",		"The dominant in four by four (1)");
		put("dominant-conjunct24-4",	"La dominante en dos por cuatro (2)",			"The dominant in two by four (2)");
		put("dominant-conjunct34-4",	"La dominante en tres por cuatro (2)",			"The dominant in three by four (2)");
		put("dominant-conjunct44-4",	"La dominante en cuatro por cuatro (2)",		"The dominant in four by four (2)");
		put("mediant24-4",				"La mediante en dos por cuatro",				"The mediant in two by four");
		put("mediant34-4",				"La mediante en tres por cuatro",				"The mediant in three by four");
		put("mediant44-4",				"La mediante en cuatro por cuatro",				"The mediant in four by four");
		put("submediant24-4",			"La submediante en dos por cuatro",				"The submediant in two by four");
		put("submediant34-4",			"La submediante en tres por cuatro",			"The submediant in three by four");
		put("submediant44-4",			"La submediante en cuatro por cuatro",			"The submediant in four by four");
		put("subdominant24-4",			"La subdominante en dos por cuatro",			"The subdominant in two by four");
		put("subdominant34-4",			"La subdominante en tres por cuatro",			"The subdominant in three by four");
		put("subdominant44-4",			"La subdominante en cuatro por cuatro",			"The subdominant in four by four");
		put("tetrachords-intro-4",		"Introducción a tetracordos",					"Introduction to tetrachords");
		put("tetrachords-lower24-4",	"Tetracordo inferior en dos por cuatro",		"Lower tetrachord in two by four");
		put("tetrachords-lower34-4",	"Tetracordo inferior en tres por cuatro",		"Lower tetrachord in three by four");
		put("tetrachords-lower44-4",	"Tetracordo inferior en cuatro por cuatro",		"Lower tetrachord in four by four");
		put("tetrachords-upper24-4",	"Tetracordo superior en dos por cuatro",		"Upper tetrachord in two by four");
		put("tetrachords-upper34-4",	"Tetracordo superior en tres por cuatro",		"Upper tetrachord in three by four");
		put("tetrachords-upper44-4",	"Tetracordo superior en cuatro por cuatro",		"Upper tetrachord in four by four");
		put("tetrachords-both24-4",		"Ambos tetracordos en dos por cuatro",			"Both tetrachords in two by four");
		put("tetrachords-both34-4",		"Ambos tetracordos en tres por cuatro",			"Both tetrachords in three by four");
		put("tetrachords-both44-4",		"Ambos tetracordos en cuatro por cuatro",		"Both tetrachords in four by four");
		put("half24-1-4",				"Blancas en dos por cuatro (1)",				"Half notes in two by four (1)");
		put("half24-2-4",				"Blancas en dos por cuatro (2)",				"Half notes in two by four (2)");
		put("half24-3-4",				"Blancas en dos por cuatro (3)",				"Half notes in two by four (3)");
		put("half24-4-4",				"Blancas en dos por cuatro (4)",				"Half notes in two by four (4)");
		put("half-quarter24-1-4",		"Blancas y negras en dos por cuatro (1)",		"Half and quarter notes in two by four (1)");
		put("half-quarter24-2-4",		"Blancas y negras en dos por cuatro (2)",		"Half and quarter notes in two by four (2)");
		put("half-quarter24-3-4",		"Blancas y negras en dos por cuatro (3)",		"Half and quarter notes in two by four (3)");
		put("half-quarter24-4-4",		"Blancas y negras en dos por cuatro (4)",		"Half and quarter notes in two by four (4)");
		put("half-quarter24-5-4",		"Blancas y negras en dos por cuatro (5)",		"Half and quarter notes in two by four (5)");
		put("half-quarter24-6-4",		"Blancas y negras en dos por cuatro (6)",		"Half and quarter notes in two by four (6)");
		put("half-quarter34-1-4",		"Blancas y negras en tres por cuatro (1)",		"Half and quarter notes in three by four (1)");
		put("half-quarter34-2-4",		"Blancas y negras en tres por cuatro (2)",		"Half and quarter notes in three by four (2)");
		put("half-quarter34-3-4",		"Blancas y negras en tres por cuatro (3)",		"Half and quarter notes in three by four (3)");
		put("half-quarter34-4-4",		"Blancas y negras en tres por cuatro (4)",		"Half and quarter notes in three by four (4)");
		put("half-quarter34-5-4",		"Blancas y negras en tres por cuatro (5)",		"Half and quarter notes in three by four (5)");
		put("half-quarter34-6-4",		"Blancas y negras en tres por cuatro (6)",		"Half and quarter notes in three by four (6)");
		put("half44-1-4",				"Blancas en cuatro por cuatro (1)",				"Half notes in four by four (1)");
		put("half44-2-4",				"Blancas en cuatro por cuatro (2)",				"Half notes in four by four (2)");
		put("half44-3-4",				"Blancas en cuatro por cuatro (3)",				"Half notes in four by four (3)");
		put("half44-4-4",				"Blancas en cuatro por cuatro (4)",				"Half notes in four by four (4)");
		put("half44-5-4",				"Blancas en cuatro por cuatro (5)",				"Half notes in four by four (5)");
		put("half44-6-4",				"Blancas en cuatro por cuatro (6)",				"Half notes in four by four (6)");
		put("half-quarter44-1-4",		"Blancas y negras en cuatro por cuatro (1)",	"Half and quarter notes in four by four (1)");
		put("half-quarter44-2-4",		"Blancas y negras en cuatro por cuatro (2)",	"Half and quarter notes in four by four (2)");
		put("half-quarter44-3-4",		"Blancas y negras en cuatro por cuatro (3)",	"Half and quarter notes in four by four (3)");
		put("half-quarter44-4-4",		"Blancas y negras en cuatro por cuatro (4)",	"Half and quarter notes in four by four (4)");
		put("half-quarter44-5-4",		"Blancas y negras en cuatro por cuatro (5)",	"Half and quarter notes in four by four (5)");
		put("half-quarter44-6-4",		"Blancas y negras en cuatro por cuatro (6)",	"Half and quarter notes in four by four (6)");
		put("half-full-1-5",			"Extra 1",										"Extra 1");
		put("half-full-2-5",			"Extra 2",										"Extra 2");
		put("whole-1-4",				"Redondas (1)",									"Whole notes (1)");
		put("whole-2-4",				"Redondas (2)",									"Whole notes (2)");
		put("whole-3-4",				"Redondas (3)",									"Whole notes (3)");
		put("whole-half-1-4",			"Redondas y blancas en cuatro por cuatro (1)",	"Whole and half notes in four by four (1)");
		put("whole-half-2-4",			"Redondas y blancas en cuatro por cuatro (2)",	"Whole and half notes in four by four (2)");
		put("whole-half-3-4",			"Redondas y blancas en cuatro por cuatro (3)",	"Whole and half notes in four by four (3)");
		put("whole-half-4-4",			"Redondas y blancas en cuatro por cuatro (4)",	"Whole and half notes in four by four (4)");
		put("whole-half-quarter-1-4",	"Redondas, blancas y negras en cuatro por cuatro (1)",	"Whole, half, and quarter notes in four by four (1)");
		put("whole-half-quarter-2-4",	"Redondas, blancas y negras en cuatro por cuatro (2)",	"Whole, half, and quarter notes in four by four (2)");
		put("whole-half-quarter-3-4",	"Redondas, blancas y negras en cuatro por cuatro (3)",	"Whole, half, and quarter notes in four by four (3)");
		put("whole-half-quarter-4-4",	"Redondas, blancas y negras en cuatro por cuatro (4)",	"Whole, half, and quarter notes in four by four (4)");
		put("whole-half-quarter-5-4",	"Redondas, blancas y negras en cuatro por cuatro (5)",	"Whole, half, and quarter notes in four by four (5)");
		put("whole-half-quarter-6-4",	"Redondas, blancas y negras en cuatro por cuatro (6)",	"Whole, half, and quarter notes in four by four (6)");
		
		put("eighths-combinations-24-4",		"Combinaciones de corcheas en dos por cuatro",			"Eighth note combinations in two by four");
		put("eighths-grouping-24-4",			"Agrupación de corcheas en dos por cuatro",				"Eighth note grouping in two by four");
		put("eighths-24-1-4",					"Corcheas en dos por cuatro (1)",						"Eighth notes in two by four (1)");
		put("eighths-24-2-4",					"Corcheas en dos por cuatro (2)",						"Eighth notes in two by four (2)");
		put("eighths-24-3-4",					"Corcheas en dos por cuatro (3)",						"Eighth notes in two by four (3)");
		put("eighths-24-4-4",					"Corcheas en dos por cuatro (4)",						"Eighth notes in two by four (4)");
		put("eighths-24-5-4",					"Corcheas en dos por cuatro (5)",						"Eighth notes in two by four (5)");
		
		put("eighths-combinations-34-4",		"Combinaciones de corcheas en tres por cuatro",			"Eighth note combinations in three by four");
		put("eighths-grouping-34-4",			"Agrupación de corcheas en tres por cuatro",			"Eighth note grouping in three by four");
		put("eighths-34-1-4",					"Corcheas en tres por cuatro (1)",						"Eighth notes in three by four (1)");
		put("eighths-34-2-4",					"Corcheas en tres por cuatro (2)",						"Eighth notes in three by four (2)");
		put("eighths-34-3-4",					"Corcheas en tres por cuatro (3)",						"Eighth notes in three by four (3)");
		put("eighths-34-4-4",					"Corcheas en tres por cuatro (4)",						"Eighth notes in three by four (4)");
		put("eighths-34-5-4",					"Corcheas en tres por cuatro (5)",						"Eighth notes in three by four (5)");
		
		put("eighths-combinations-44-4",		"Combinaciones de corcheas en cuatro por cuatro",			"Eighth note combinations in four by four");
		put("eighths-grouping-quarter-44-4",	"Agrupación de corcheas por negras en cuatro por cuatro",	"Eighth note grouping by the quarter in four by four");
		put("eighths-grouping-34-4",			"Agrupación de corcheas por blancas en cuatro por cuatro",	"Eighth note grouping by the half in four by four");
		put("eighths-44-1-4",					"Corcheas en cuatro por cuatro (1)",						"Eighth notes in four by four (1)");
		put("eighths-44-2-4",					"Corcheas en cuatro por cuatro (2)",						"Eighth notes in four by four (2)");
		put("eighths-44-3-4",					"Corcheas en cuatro por cuatro (3)",						"Eighth notes in four by four (3)");
		put("eighths-44-4-4",					"Corcheas en cuatro por cuatro (4)",						"Eighth notes in four by four (4)");
		put("eighths-44-5-4",					"Corcheas en cuatro por cuatro (5)",						"Eighth notes in four by four (5)");
		
		put("2nddegree-4",				"Primero y segundo grados, tónica y supertónica",	"First and second degrees, tonic and supertonic");
		put("3rddegree-4",				"Hasta el tercer grado o mediante",					"Up to the third degree or mediant");
		put("4thdegree-4",				"Hasta el cuarto grado o subdominante",				"Up to the fourth degree or subdominant");
		put("5thdegree-5",				"Hasta el quinto grado o dominante",				"Up to the fifth degree or dominant");
		put("6thdegree-5",				"Hasta el sexto grado or submediante",				"Up to the sixth degree or submediant");
		put("7thdegree-6",				"Hasta el séptimo grado o sensible",				"Up to the seventh degree or leading tone");
		put("8thdegree-6",				"Hasta la tónica superior",							"Up to the upper tonic");
		put("2nds-6",					"Segundas ascendentes y descendentes",				"Ascending and descending seconds");
		put("upto3rds-5",				"Terceras ascendentes",								"Ascending thirds");
		put("downto3rds-5",				"Terceras descendentes",							"Descending thirds");
		put("upto4ths-5",				"Cuartas ascendentes",								"Ascending fourths");
		put("downto4ths-5",				"Cuartas descendentes",								"Descending fourths");
		put("upto5ths-5",				"Quintas ascendentes",								"Ascending fifths");
		put("downto5ths-5",				"Quintas descendentes",								"Descending fifths");
		put("novenabeethoven-4",		"Presentación del Tutor de Solfeo",					"Tutor of Solfege Presentation");
		/*put("","", "");
		put("","", "");*/
	}
	private void put(String lessonName, final String titleEs, final String titleEn){
		put(lessonName, new HashMap<Lang, String>(){
			{
				put(Lang.ES, titleEs);
				put(Lang.EN, titleEn);
			}
		});
	}
}
