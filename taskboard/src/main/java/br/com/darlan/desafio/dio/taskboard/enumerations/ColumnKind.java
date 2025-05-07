package br.com.darlan.desafio.dio.taskboard.enumerations;

public enum ColumnKind {
    INITIAL,
    FINAL,
    CANCEL,
    PENDING;

    public static ColumnKind fromName(String kindName) {
        for(ColumnKind kind : ColumnKind.values()) {
            if(kind.name().equals(kindName)) {
                return kind;
            }
        }
        return null;
    }
}
