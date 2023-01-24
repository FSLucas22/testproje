package view;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Formatador {
    private final DateTimeFormatter formatData;
    private final DecimalFormat formatDecimal;
    public Formatador(DateTimeFormatter formatData, DecimalFormat formatDecimal) {
        this.formatData = formatData;
        this.formatDecimal = formatDecimal;
    }
    public Formatador() {
        this.formatDecimal = new DecimalFormat("#,##0.00");;
        this.formatData = DateTimeFormatter.ofPattern("dd/MM/yyyy");;
    }
    static String criarStringEspacada(int espaco, Object... campos) {
        StringBuilder resultado = new StringBuilder();
        for (Object campo : campos) {
            resultado.append(String.format("%-" + espaco + "s", campo.toString()));
            resultado.append(" ");
        }
        return resultado.toString();
    }
    public static String criarStringEspacada(Object ... campos) {
        return criarStringEspacada(20, campos);
    }

    public String formatarData (LocalDate data){
        return data.format(formatData);
    }
    public String formatarDecimal(BigDecimal salario) {
        return formatDecimal.format(salario);
    }
}
