module GalagaRacerFX {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires jimObjModelImporterJFX;
	
	opens application to javafx.graphics, javafx.fxml;
}
