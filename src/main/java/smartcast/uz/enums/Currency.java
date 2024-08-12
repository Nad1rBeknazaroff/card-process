package smartcast.uz.enums;

public enum Currency {
    UZS(1), // in tiyin
    USD(12_700);

    Currency(long rate){
        this.exchangeRate = rate;
    }
    private long exchangeRate;

    public long getExchangeRate(){
        return exchangeRate;
    }
}
