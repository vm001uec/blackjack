package com.vineet.model;

import com.vineet.game.IActionStrategy;

import java.util.Objects;

public class Player {
    private String name;
    private Wallet wallet;
    private PlayerType playerType;
    private IActionStrategy actionStrategy;

    public Player(String name, PlayerType playerType, int initialMoney, IActionStrategy strategy) {
        this.name = name;
        this.playerType = playerType;
        this.actionStrategy = strategy;
        wallet = new Wallet(initialMoney);

    }

    public Wallet getWallet() {
        return wallet;
    }

    public String getName() {
        return name;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public IActionStrategy getActionStrategy() {
        return actionStrategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return playerType.name() + " [" +
                "name='" + name + ",\t" + wallet + " ]";

    }

    public class Wallet {
        private int initialMoney;
        private int balance;
        private int onBetMoney;

        public Wallet(int initialMoney) {
            this.initialMoney = initialMoney;
            this.balance = initialMoney;
        }

        public int getInitialMoney() {
            return initialMoney;
        }

        public int getBalance() {
            return balance;
        }

        public int getOnBetMoney() {
            return onBetMoney;
        }

        public void credit(int bal) {
            if (bal < 0) {
                throw new IllegalArgumentException(" Negative credit passed");
            }
            balance += bal;
        }

//        public int debitBetMoney() {
//            int betMoney = onBetMoney;
//            onBetMoney = 0;
//            return betMoney;
//        }

        public int unreserveBetMoney() {
            credit(onBetMoney);
            int betMoney = onBetMoney;
            onBetMoney = 0;
            return betMoney;
        }

        public boolean reserveToBet(int bal) {
            if (debit(bal)) {
                onBetMoney += bal;
                return true;
            } else {
                return false;
            }
        }

        public boolean debit(int bal) {
            if (bal < 0) {
                throw new IllegalArgumentException(" Negative debit passed");
            }

            if (balance < bal && playerType.equals(PlayerType.PLAYER)) {
                throw new IllegalArgumentException(" Cannot debit more than balance");
            }
            balance -= bal;
            return true;
        }

        @Override
        public String toString() {
            return
                    "initialMoney=" + initialMoney +
                            ", balance=" + balance;
        }
    }
}
