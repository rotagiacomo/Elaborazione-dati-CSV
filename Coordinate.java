public class Coordinate {
    private double latitudine;
    private double longitudine;

    public void setLatitudine(double latitudine){
        if (latitudine >= -90 && latitudine <= 90){
            this.latitudine = latitudine;
        }else {
            this.latitudine = 200;
        }
    }

    public void setLongitudine(double longitudine) {
        if (longitudine >= -180 && longitudine <= 180){
            this.longitudine = longitudine;
        }else {
            this.longitudine = 200;
        }
    }

    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public Coordinate(){
        latitudine = 0;
        longitudine = 0;
    }

    public Coordinate(double latitudine, double longitudine){
        setLatitudine(latitudine);
        setLongitudine(longitudine);
    }

    public boolean equals(Coordinate cordinateDaConfrontare){
        return latitudine == cordinateDaConfrontare.latitudine && longitudine == cordinateDaConfrontare.longitudine;
    }

    public boolean isInArea(Coordinate minime, Coordinate massime){
        if (minime.latitudine > massime.latitudine){
            double temp = minime.latitudine;
            minime.latitudine = massime.latitudine;
            massime.latitudine = temp;
        }
        if (minime.longitudine > massime.longitudine){
            double temp = minime.longitudine;
            minime.longitudine = massime.longitudine;
            massime.longitudine = temp;
        }
        return latitudine >= minime.latitudine && latitudine <= massime.latitudine && longitudine >= minime.longitudine && longitudine <= massime.longitudine;
    }

    public String toString(){
        return "Coordinate[" + latitudine + ", " + longitudine + "]";
    }
}
