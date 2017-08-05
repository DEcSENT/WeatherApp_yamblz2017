package com.mishkun.weatherapp.domain.entities;

public class Temperature {
    private final double kelvinDegrees;

    public Temperature(double kelvinDegrees) {
        this.kelvinDegrees = kelvinDegrees;
    }

    public static Temperature fromKelvin(double kelvinDegrees) {
        return new Temperature(kelvinDegrees);
    }

    public double getKelvinDegrees(){
        return kelvinDegrees;
    }

    public double getFahrenheitDegrees(){
        // K to F formula got from here: http://www.rapidtables.com/convert/temperature/how-kelvin-to-fahrenheit.htm
        return  kelvinDegrees * 9/5 - 459.67;
    }

    public double getCelsiusDegrees(){
        return kelvinDegrees - 273;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Temperature that = (Temperature) o;

        return Double.compare(that.kelvinDegrees, kelvinDegrees) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(kelvinDegrees);
        return (int) (temp ^ (temp >>> 32));
    }
}
