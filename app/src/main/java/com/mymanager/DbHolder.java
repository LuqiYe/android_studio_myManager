package com.mymanager;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.exceptions.RealmException;

public class DbHolder {
    public DbHolder() {
    }

    public List<Account> getAccounts() {
        try {

            final Realm sd = Realm.getDefaultInstance();
            List<Account> accountBean = sd.where(Account.class).findAll();
            return accountBean;
        } catch (RealmException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Account> SerachAcc(String from, String to) {
        try {
            final long f = ConstantClass.getTimeMillies(from);
            final long t = ConstantClass.getTimeMillies(to);


            final Realm sd = Realm.getDefaultInstance();
            final List<Account> accountBean = sd.where(Account.class).findAll();
            final List<Account> accountBean1 =  new ArrayList<>();
            sd.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for(Account accountBean2:accountBean){
                        long m = ConstantClass.getTimeMillies(accountBean2.getDate());
                        if(m>=f && m<=t){
                            accountBean1.add(accountBean2);
                        }
                    }
                }
            });


            return accountBean1;
        } catch (RealmException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getBalance() {
        final double[] add = {0};
        final double[] minus = {0};
        double total = 0;
        try {
            final Realm sd = Realm.getDefaultInstance();
            final List<Account> accountBean = sd.where(Account.class).findAll();
            sd.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for(Account accountBean2:accountBean){
                        if(accountBean2.getType().equals(ConstantClass.added)){
                            add[0] +=Double.parseDouble(accountBean2.getAmount());
                        }
                        else{
                            minus[0] +=Double.parseDouble(accountBean2.getAmount());
                        }
                    }
                }
            });


        } catch (RealmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

        total = add[0] - minus[0];

        return total;
    }
    public Account getAccountById(String Id) {
        try {

            final Realm sd = Realm.getDefaultInstance();
            Account accountBean = sd.where(Account.class).equalTo(ConstantClass.transId, Id).findFirst();
            return accountBean;
        } catch (RealmException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return null;
    }
    public void saveAccount(final Account accountBean) {
        try {
            final Realm sd = Realm.getDefaultInstance();
            sd.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(accountBean);
                }
            });
        } catch (RealmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

    }
    public void saveAccounts(final List<Account> l1) {
        try {
            final Realm sd = Realm.getDefaultInstance();
            sd.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for (Account accountBean : l1) {
                        realm.copyToRealmOrUpdate(accountBean);

                    }
                }
            });
        } catch (RealmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

    }

}
