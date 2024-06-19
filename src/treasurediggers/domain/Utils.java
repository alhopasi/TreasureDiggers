package treasurediggers.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javafx.scene.input.KeyCode;

public class Utils {
    
    public static List<String> readFile(String file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lines;
    }

    public static Map<String,KeyCode> initKeymap() {
        Map<String,KeyCode> keys = new HashMap<>();
        keys.put("a",KeyCode.A);
        keys.put("b",KeyCode.B);
        keys.put("c",KeyCode.C);
        keys.put("d",KeyCode.D);
        keys.put("e",KeyCode.E);
        keys.put("f",KeyCode.F);
        keys.put("g",KeyCode.G);
        keys.put("h",KeyCode.H);
        keys.put("i",KeyCode.I);
        keys.put("j",KeyCode.J);
        keys.put("k",KeyCode.K);
        keys.put("l",KeyCode.L);
        keys.put("m",KeyCode.M);
        keys.put("n",KeyCode.N);
        keys.put("o",KeyCode.O);
        keys.put("p",KeyCode.P);
        keys.put("q",KeyCode.Q);
        keys.put("r",KeyCode.R);
        keys.put("s",KeyCode.S);
        keys.put("t",KeyCode.T);
        keys.put("u",KeyCode.U);
        keys.put("v",KeyCode.V);
        keys.put("w",KeyCode.W);
        keys.put("x",KeyCode.X);
        keys.put("y",KeyCode.Y);
        keys.put("z",KeyCode.Z);
        keys.put("A",KeyCode.A);
        keys.put("B",KeyCode.B);
        keys.put("C",KeyCode.C);
        keys.put("D",KeyCode.D);
        keys.put("E",KeyCode.E);
        keys.put("F",KeyCode.F);
        keys.put("G",KeyCode.G);
        keys.put("H",KeyCode.H);
        keys.put("I",KeyCode.I);
        keys.put("J",KeyCode.J);
        keys.put("K",KeyCode.K);
        keys.put("L",KeyCode.L);
        keys.put("M",KeyCode.M);
        keys.put("N",KeyCode.N);
        keys.put("O",KeyCode.O);
        keys.put("P",KeyCode.P);
        keys.put("Q",KeyCode.Q);
        keys.put("R",KeyCode.R);
        keys.put("S",KeyCode.S);
        keys.put("T",KeyCode.T);
        keys.put("U",KeyCode.U);
        keys.put("V",KeyCode.V);
        keys.put("W",KeyCode.W);
        keys.put("X",KeyCode.X);
        keys.put("Y",KeyCode.Y);
        keys.put("Z",KeyCode.Z);
        keys.put("0",KeyCode.DIGIT0);
        keys.put("1",KeyCode.DIGIT1);
        keys.put("2",KeyCode.DIGIT2);
        keys.put("3",KeyCode.DIGIT3);
        keys.put("4",KeyCode.DIGIT4);
        keys.put("5",KeyCode.DIGIT5);
        keys.put("6",KeyCode.DIGIT6);
        keys.put("7",KeyCode.DIGIT7);
        keys.put("8",KeyCode.DIGIT8);
        keys.put("9",KeyCode.DIGIT9);
        keys.put("-",KeyCode.MINUS);
        keys.put("=",KeyCode.EQUALS);
        keys.put("*",KeyCode.ASTERISK);
        keys.put("(",KeyCode.LEFT_PARENTHESIS);
        keys.put(")",KeyCode.RIGHT_PARENTHESIS);
        keys.put("_",KeyCode.UNDERSCORE);
        keys.put("+",KeyCode.PLUS);
        keys.put("[",KeyCode.OPEN_BRACKET);
        keys.put("]",KeyCode.CLOSE_BRACKET);
        keys.put(";",KeyCode.SEMICOLON);
        keys.put(":",KeyCode.COLON);
        keys.put(",",KeyCode.COMMA);
        keys.put(".",KeyCode.PERIOD);
        keys.put("/",KeyCode.SLASH);
		keys.put("NUMPAD0",KeyCode.NUMPAD0);
		keys.put("NUMPAD1",KeyCode.NUMPAD1);
		keys.put("NUMPAD2",KeyCode.NUMPAD2);
		keys.put("NUMPAD3",KeyCode.NUMPAD3);
		keys.put("NUMPAD4",KeyCode.NUMPAD4);
		keys.put("NUMPAD5",KeyCode.NUMPAD5);
		keys.put("NUMPAD6",KeyCode.NUMPAD6);
		keys.put("NUMPAD7",KeyCode.NUMPAD7);
		keys.put("NUMPAD8",KeyCode.NUMPAD8);
		keys.put("NUMPAD9",KeyCode.NUMPAD9);
        keys.put("KP_UP",KeyCode.KP_UP);
        keys.put("KP_DOWN",KeyCode.KP_DOWN);
        keys.put("KP_LEFT",KeyCode.KP_LEFT);
        keys.put("KP_RIGHT",KeyCode.KP_RIGHT);
        return keys;
    }
}
