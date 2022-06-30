package ru.nsu.litvinenko.lab3.Game.Model;

import java.awt.*;

public class FieldCell {
    public int value;
    public Color color;
    public FieldCell(Color _color, int _value){
        value = _value;
        color = _color;
    }
}
