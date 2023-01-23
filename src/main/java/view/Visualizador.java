package view;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Visualizador {
    private final DateTimeFormatter formatData;
    private final DecimalFormat formatSalario;
    public Visualizador(DateTimeFormatter formatData, DecimalFormat formatSalario) {
        this.formatData = formatData;
        this.formatSalario = formatSalario;
    }
    public Visualizador() {
        this.formatSalario = new DecimalFormat("#,##0.00");;
        this.formatData = DateTimeFormatter.ofPattern("dd/MM/yyyy");;
    }
    static String criaStringEspacada(int espaco, Object... campos) {
        StringBuilder resultado = new StringBuilder();
        for (Object campo : campos) {
            resultado.append(String.format("%-" + espaco + "s", campo.toString()));
            resultado.append(" ");
        }
        return resultado.toString();
    }
    public static String criaStringEspacada(Object ... campos) {
        return criaStringEspacada(20, campos);
    }

    public String formataData (LocalDate data){
        return data.format(formatData);
    }
    public String formataSalario(BigDecimal salario) {
        return formatSalario.format(salario);
    }
}
