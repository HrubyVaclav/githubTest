/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import enums.EnumKraj;
import enums.EnumPozice;
import enums.EnumPrioritaTyp;
import static enums.EnumPrioritaTyp.POCET_OBYVATEL;
import enums.eTypProhl;
import java.util.Comparator;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import kolekce.AbstrHeap;
import kolekce.TableException;
import sprava.AgendaKraj;
import sprava.Obec;
import sprava.Obyvatele;

/**
 *
 * @author hruby
 */
public class ProgObyvatele extends Application {

    private static final int WINDOW_HEIGHT = 800;
    private static final int WINDOW_WIDTH = 1700;
    static boolean isOkClicked = false;
    private final Obyvatele obyv = new Obyvatele();
    private final AgendaKraj agenda = new AgendaKraj();
    private static ObservableList<Obec> zobrazovaneObce = FXCollections.observableArrayList();
    private ComboBox cbZvolenyKraj;
    private ComboBox cbZvolenaPozice;
    private ComboBox cbZvolenaPriorita;
    private ListView<Obec> listView;

    private Comparator<Obec> komparatorNazev = (o1, o2) -> o1.getObec().compareTo(o2.getObec());
    private Comparator<Obec> komparatorPocetObyvatel = (o1, o2) -> o1.getCelkem() - o2.getCelkem();
    private final AbstrHeap halda = new AbstrHeap(komparatorPocetObyvatel);

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Seznam obc??");
        listView = createListView();
        ControlPanelVBox controlPanelVerticalZobraz = createControlPanelVerticalZobraz();
        ControlPanelVBox controlPanelTabulka = createControlPanelTable();
        ControlPanelVBox controlPanelHeap = createControlPanelHeap();
        HBox hbox = new HBox();
        hbox.getChildren().addAll(listView, controlPanelVerticalZobraz, controlPanelTabulka, controlPanelHeap);

        ControlPanelHBox controlPanelHorizontal = createControlPanelHorizontal();
        root.getChildren().addAll(hbox, controlPanelHorizontal);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ListView<Obec> createListView() {
        ListView<Obec> list = new ListView<>();
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list.setMinHeight(WINDOW_HEIGHT - 60);
        list.setMinWidth(WINDOW_WIDTH - 650);
        list.setCellFactory((cell) -> {
            return new ListCell<Obec>() {
                @Override
                public void updateItem(Obec item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty && item != null) {
                        setText(item.toString());
                        setFont(Font.font("Courier New", 13));
                    } else {
                        setText(null);
                    }
                }
            };

        });

        return list;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private ControlPanelVBox createControlPanelVerticalZobraz() {
        ControlPanelVBox controlPanel = new ControlPanelVBox();
        cbZvolenyKraj = controlPanel.generateCb(EnumKraj.getKraje());
        controlPanel.addButton("Zobraz v??echny obce", zobrazVsechnyObceHandler);
        controlPanel.addButton("Zobraz obce nad pr??m??r (v??e)", zobrazVsechnyNadPrumer);
        controlPanel.addButton("Zobraz pr??m??r v??ech obc??", zobrazPrumerVsechObci);
        controlPanel.addLabel("Konkr??tn?? kraj");
        controlPanel.addComboBox(cbZvolenyKraj);
        controlPanel.addButton("Zobraz obce", zobrazObceHandler);
        controlPanel.addButton("Zobraz obce nad pr??m??r", zobrazObceNadPrumerHandler);
        controlPanel.addButton("Zobraz pr??m??r", zobrazPrumerHandler);
        controlPanel.addButton("Zru??", zrusHandler);
        controlPanel.addLabel("Operace");
        cbZvolenaPozice = controlPanel.generateCb(EnumPozice.getPozice());
        controlPanel.addComboBox(cbZvolenaPozice);
        controlPanel.addButton("Vloz obec", vlozObecHandler);
        controlPanel.addButton("Zp????stupni obec", zpristupniObecHandler);
        controlPanel.addButton("Odeber obec", odeberObecHandler);
        return controlPanel;

    }

    private ControlPanelVBox createControlPanelTable() {
        ControlPanelVBox controlPanel = new ControlPanelVBox();
        controlPanel.addLabel("Strom");
        controlPanel.addButton("Vlo??", tabulkaVlozObecHandler);
        controlPanel.addButton("Najdi", tabulkaNajdiObecHandler);
        controlPanel.addButton("Odeber", tabulkaOdeberObecHandler);
        controlPanel.addButton("Vybuduj", tabulkaVybudujHandler);
        controlPanel.addButton("Generuj", tabulkaGenerujHandler);
        controlPanel.addButton("Zobraz inorder", tabulkaZobrazInOrderHandler);
        controlPanel.addButton("Zobraz do ????rky", tabulkaZobrazDoSirkyHandler);
        controlPanel.addButton("P??iprav test strom", tabulkaPripravTestStromHandler);
        return controlPanel;

    }

    private ControlPanelVBox createControlPanelHeap() {
        ControlPanelVBox controlPanel = new ControlPanelVBox();
        cbZvolenaPriorita = controlPanel.generateCb(EnumPrioritaTyp.values());
        controlPanel.addLabel("Priority queue");
        controlPanel.addButton("Vybuduj", heapVybudujHandler);
        controlPanel.addComboBox(cbZvolenaPriorita);
        controlPanel.addButton("Reorganizuj", heapReorganizujHandler);
        controlPanel.addButton("Zru??", heapZrusHandler);
        controlPanel.addButton("Vlo??", heapVlozHandler);
        controlPanel.addButton("Odeber max", heapOdeberMaxHandler);
        controlPanel.addButton("Zp????stupni max", heapZpristupniMaxHandler);
        controlPanel.addButton("Zobraz do ??????ky", heapZobrazDoSirkyHandler);
        controlPanel.addButton("Zobraz do hloubky", heapZobrazDoHloubkyHandler);
        return controlPanel;
    }

    private ControlPanelHBox createControlPanelHorizontal() {
        ControlPanelHBox controlPanel = new ControlPanelHBox();
        controlPanel.addButton("Importuj data", importDataHandler);
        controlPanel.addButton("Na??ti textov?? soubor", nactiTextFileHandler);
        controlPanel.addButton("Ulo?? textov?? soubor", ulozTextFileHandler);
        controlPanel.addButton("Generuj", generujHandler);
        controlPanel.addButton("Zru?? cel?? seznam", zrusCelySeznamHandler);
        return controlPanel;
    }

    private final EventHandler<ActionEvent> zobrazVsechnyNadPrumer = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            obnovListView();
            obyv.zobrazObceNadPrumer(EnumKraj.VSECHNY);
        }
    };

    private final EventHandler<ActionEvent> zobrazPrumerVsechObci = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                float prumer = obyv.zjistiPrumer(EnumKraj.VSECHNY);
                if (Float.isNaN(prumer)) { // Pokud je dan?? line??rn?? seznam pr??zdn??
                    throw new Exception();
                }
                vypisDialogSPrumerem(prumer);
            } catch (Exception e) {
                vypisChybovyDialog("Seznam je pr??zdn??!");
            }
        }
    };

    private final EventHandler<ActionEvent> zobrazVsechnyObceHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            obnovListView();
            obyv.zobrazObce(EnumKraj.VSECHNY);
        }
    };

    private final EventHandler<ActionEvent> zobrazObceHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            obnovListView();
            obyv.zobrazObce((EnumKraj) cbZvolenyKraj.getSelectionModel().getSelectedItem());
        }
    };

    private final EventHandler<ActionEvent> zobrazObceNadPrumerHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            obnovListView();
            obyv.zobrazObceNadPrumer((EnumKraj) cbZvolenyKraj.getSelectionModel().getSelectedItem());
        }
    };

    private final EventHandler<ActionEvent> zobrazPrumerHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                float prumer = obyv.zjistiPrumer((EnumKraj) cbZvolenyKraj.getSelectionModel().getSelectedItem());
                if (Float.isNaN(prumer)) { // Pokud je dan?? line??rn?? seznam pr??zdn??
                    throw new Exception();
                }
                vypisDialogSPrumerem(prumer);
            } catch (Exception e) {
                vypisChybovyDialog("Seznam je pr??zdn??!");
            }
        }
    };

    private final EventHandler<ActionEvent> vlozObecHandler = event -> {
        try {
            Obec obec = new Obec();
            Stage dialog = new DialogVloz(obec);
            if (dialog != null) {
                dialog.showAndWait();
                if (isOkClicked) {
                    obyv.vlozObec(obec, (EnumPozice) cbZvolenaPozice.getSelectionModel().getSelectedItem(),
                            (EnumKraj) cbZvolenyKraj.getSelectionModel().getSelectedItem());
                }
            }
        } catch (Exception e) {
            vypisChybovyDialog("Nov?? obec nelze vlo??it na zvolen?? m??sto!");
        }
    };
    private final EventHandler<ActionEvent> zpristupniObecHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Obec obec = obyv.zpristupniObec((EnumPozice) cbZvolenaPozice.getSelectionModel().getSelectedItem(),
                        (EnumKraj) cbZvolenyKraj.getSelectionModel().getSelectedItem());
                listView.getSelectionModel().select(obec);
            } catch (Exception e) {
                vypisChybovyDialog("Seznam je pr??zdn??!");
            }
        }
    };

    private final EventHandler<ActionEvent> odeberObecHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                obyv.odeberObec((EnumPozice) cbZvolenaPozice.getSelectionModel().getSelectedItem(),
                        (EnumKraj) cbZvolenyKraj.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                vypisChybovyDialog("Seznam je pr??zdn??!");
            }
        }
    };

    private final EventHandler<ActionEvent> importDataHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                obyv.importData("custom.csv");
            } catch (Exception e) {
                vypisChybovyDialog("Data se nepoda??ilo importovat");
            }
        }
    };

    private final EventHandler<ActionEvent> nactiTextFileHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                obyv.zrus(EnumKraj.VSECHNY);
                obyv.nactiTextSoubor("obce.txt");
            } catch (Exception e) {
                vypisChybovyDialog("Textov?? soubor se nepoda??ilo na????st");
            }
        }
    };

    private final EventHandler<ActionEvent> ulozTextFileHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                obyv.ulozTextSoubor("obce.txt");
            } catch (Exception e) {
                vypisChybovyDialog("Data se nepoda??ilo ulo??it");
            }
        }
    };

    private final EventHandler<ActionEvent> generujHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            int pocetObci = dialogGeneruj();
            obyv.generujObce(pocetObci);
        }
    };

    private final EventHandler<ActionEvent> zrusHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            obyv.zrus((EnumKraj) cbZvolenyKraj.getSelectionModel().getSelectedItem());
        }
    };

    private final EventHandler<ActionEvent> zrusCelySeznamHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            obyv.zrus(EnumKraj.VSECHNY);
        }
    };

    private final EventHandler<ActionEvent> tabulkaVlozObecHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Obec obec = new Obec();
            Stage dialog = new DialogVloz(obec);
            if (dialog != null) {
                dialog.showAndWait();
                if (isOkClicked) {
                    try {
                        agenda.vloz(obec.getObec(), obec);
                    } catch (TableException ex) {
                        vypisChybovyDialog("Kl???? u?? je v tabulce p??itomen!");
                    }
                }
            }
        }

    };

    private final EventHandler<ActionEvent> tabulkaNajdiObecHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String key = dialogKey();
            Obec obec = agenda.najdi(key);
            if (obec == null) {
                vypisChybovyDialog("Dan?? obec v stromu neexistuje!");
            } else {
                listView.getSelectionModel().select(obec);
            }

        }
    };

    private final EventHandler<ActionEvent> tabulkaOdeberObecHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String key = dialogKey();
            Obec obec = agenda.odeber(key);
            if (obec == null) {
                vypisChybovyDialog("Dan?? obec v stromu neexistuje!");
            }
        }
    };

    private final EventHandler<ActionEvent> tabulkaVybudujHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            agenda.vybuduj();

        }
    };

    private final EventHandler<ActionEvent> tabulkaGenerujHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            int pocetObci = dialogGeneruj();
            agenda.generuj(pocetObci);

        }
    };

    private final EventHandler<ActionEvent> tabulkaZobrazInOrderHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            obnovListView();
            agenda.zobrazInOrder();

        }
    };

    private final EventHandler<ActionEvent> tabulkaZobrazDoSirkyHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            obnovListView();
            agenda.zobrazDoSirky();

        }
    };

    private final EventHandler<ActionEvent> tabulkaPripravTestStromHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            obyv.nactiTestData();
            agenda.vybuduj();
        }
    };

    private final EventHandler<ActionEvent> heapVybudujHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            halda.nastavKomparator(komparatorPocetObyvatel);
            if (Obyvatele.getObceArray() == null) {
                vypisChybovyDialog("Pole obc?? je pr??zdn??!");
            } else {
                halda.vybuduj(Obyvatele.getObceArray());
            }
        }
    };

    private final EventHandler<ActionEvent> heapReorganizujHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (cbZvolenaPriorita.getSelectionModel().getSelectedItem().equals(POCET_OBYVATEL)) {
                halda.nastavKomparator(komparatorPocetObyvatel);
            } else {
                halda.nastavKomparator(komparatorNazev);
            }
            halda.reorganizace();
        }
    };

    private final EventHandler<ActionEvent> heapZrusHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            halda.zrus();
        }
    };

    private final EventHandler<ActionEvent> heapVlozHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Obec obec = new Obec();
            Stage dialog = new DialogVloz(obec);
            if (dialog != null) {
                dialog.showAndWait();
                if (isOkClicked) {
                    halda.vloz(obec);
                }
            }

        }
    };

    private final EventHandler<ActionEvent> heapOdeberMaxHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Obec obec = (Obec) halda.odeberMax();
            if (obec == null) {
                vypisChybovyDialog("????dn?? obec nebyla odebr??na!");
            }

        }
    };

    private final EventHandler<ActionEvent> heapZpristupniMaxHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Obec obec = (Obec) halda.zpristupniMax();
                listView.getSelectionModel().select(obec);
            } catch (Exception e) {
                vypisChybovyDialog("Seznam je pr??zdn??!");
            }
        }
    };

    private final EventHandler<ActionEvent> heapZobrazDoSirkyHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            obnovListView();
            halda.vypis(eTypProhl.DO_SIRKY);
        }
    };

    private final EventHandler<ActionEvent> heapZobrazDoHloubkyHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            obnovListView();
            halda.vypis(eTypProhl.DO_HLOUBKY);
        }
    };

    public static void vypisChybovyDialog(String txt) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error dialog");
        alert.setHeaderText(null);
        alert.setContentText(txt);
        alert.showAndWait();
    }

    private void vypisDialogSPrumerem(float prumer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spo????tan?? pr??m??r");
        alert.setHeaderText(null);
        alert.setContentText("Pr??m??r vy??el: " + prumer);
        alert.showAndWait();
    }

    private void obnovListView() {
        listView.getItems().clear();
        listView.setItems(zobrazovaneObce);
    }

    public static void addToList(Obec obec) {
        zobrazovaneObce.add(obec);
    }

    private int dialogGeneruj() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Zaps??n?? po??tu generovan??ch obc??");
        dialog.setHeaderText(null);
        dialog.setContentText("Zapi??te po??adovan?? po??et obc??: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                int pocet = Integer.parseInt(dialog.getResult());
                if (pocet <= 0) {
                    throw new Exception();
                }
                return pocet;
            } catch (Exception e) {
                vypisChybovyDialog("Po??et mus?? b??t cel?? kladn?? ????slo");
            }
        }
        return 0;
    }

    private String dialogKey() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Zaps??n?? n??zvu obce");
        dialog.setHeaderText("Zadejte n??zev obce");
        dialog.setContentText("N??zev obce: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                String key = dialog.getResult();
                if (key.trim().length() == 0) {
                    throw new Exception();
                }
                return result.get();
            } catch (Exception e) {
                vypisChybovyDialog("N??zev obce nem????e b??t pr??zdn??!");
            }
        }
        return null;
    }

}
