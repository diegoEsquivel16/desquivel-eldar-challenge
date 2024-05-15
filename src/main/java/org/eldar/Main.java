package org.eldar;

import org.eldar.exception.InvalidOperationException;
import org.eldar.model.Brand;
import org.eldar.model.CreditCard;
import org.eldar.repository.CreditCardsRepository;
import org.eldar.service.CreditCardService;
import org.eldar.util.CreditCardCreator;
import org.eldar.util.DateUtil;

public class Main {

    public static void main(String[] args) {
        System.out.println("-Ahora se van a mostrar los resultados de distintas operaciones para el Challenge Java Backend.");

        CreditCardService service = initializeCreditCardService();
        System.out.println("Lo que se hizo fue precargar en el sistema 6 tarjetas, algunas vigentes y otras ya expiradas.");

        System.out.println("\n1- Lo primero que se pidió fue invocar un metodo que devuelva toda la informacion de una tarjeta.");
        executeChallenge1(service);

        System.out.println("\n2- El siguiente ejercicio pide informar si una operacion es valida.");
        executeChallenge2(service);

        System.out.println("\n3- El siguiente ejercicio pide informar si una tarjeta es valida para operar.");
        executeChallenge3(service);

        System.out.println("\n4- El siguiente ejercicio pide identificar si una tarjeta es distinta a otra");
        executeChallenge4(service);

        System.out.println("\n5- Por ultimo se pide mediante un metodo obtener la tasa de una operacion informando marca e importe. \nTener en cuenta que se valida que el valor de la tasa este entre el 0.3% y el 5%");
        executeChallenge5(service);
    }

    private static CreditCardService initializeCreditCardService() {
        CreditCardsRepository repository = new CreditCardsRepository();
        initializeCreditCards(repository);
        return new CreditCardService(repository);
    }

    private static void executeChallenge1(CreditCardService service) {
        System.out.println("_Primero vamos a imprimir la informacion de una tarjeta valida que está en el sistema:");
        System.out.println(service.getAndPrintCreditCardInformation(validCreditCardVisa.getNumber()));
        System.out.println("_Luego la informacion de una tarjeta expirada que está en el sistema:");
        System.out.println(service.getAndPrintCreditCardInformation(expiredCreditCardAmex.getNumber()));
        System.out.println("_Y por ultimo vamos a intentar imprimir la informacion de una tarjeta que no está en el sistema:");
        System.out.println(service.getAndPrintCreditCardInformation(INVALID_CREDIT_CARD_NUMBER));
    }

    private static void executeChallenge2(CreditCardService service) {
        System.out.println("_Para eso preguntamos primero si una operacion con valor valido es posible:");
        System.out.println(service.validateAndPrintOperation(VALID_OPERATION_VALUE));
        System.out.println("_Luego hacemos la misma consulta con un valor invalida para una operacion:");
        System.out.println(service.validateAndPrintOperation(INVALID_OPERATION_VALUE));
    }

    private static void executeChallenge3(CreditCardService service) {
        System.out.println("_Primero probamos con una tarjeta valida:");
        System.out.println(service.validateAndPrintCreditCard(validCreditCardNara));
        System.out.println("_Luego probamos con una tarjeta ya vencida:");
        System.out.println(service.validateAndPrintCreditCard(expiredCreditCardNara));
    }

    private static void executeChallenge4(CreditCardService service) {
        System.out.println("_Primero probamos con una misma tarjeta:");
        System.out.println(service.compareCreditCard(validCreditCardAmex, validCreditCardAmex2));
        System.out.println("_Luego probamos con dos tarjetas distintas:");
        System.out.println(service.compareCreditCard(validCreditCardNara, expiredCreditCardAmex));
    }

    private static void executeChallenge5(CreditCardService service) {
        try {
            System.out.println("_Primero probamos el camino feliz donde probamos con una tarjeta VISA y operacion de 100:");
            System.out.println(service.calculateInterestRateForOperation(VALID_OPERATION_VALUE, Brand.VISA));
            System.out.println("_Luego con una operacion de 100 y una tarjeta NARA:");
            System.out.println(service.calculateInterestRateForOperation(VALID_OPERATION_VALUE, Brand.NARA));
            System.out.println("_Y por ultimo con una operacion de 100 y una tarjeta AMEX:");
            System.out.println(service.calculateInterestRateForOperation(VALID_OPERATION_VALUE, Brand.AMEX));
        } catch (InvalidOperationException ioe) {
            System.out.println("Ups... Algo salió mal en la demo :S");
        }
        System.out.println("_Ahora probamos con una operacion invalida y una tarjeta VISA:");
        try {
            System.out.println(service.calculateInterestRateForOperation(INVALID_OPERATION_VALUE, Brand.VISA));
        }catch (InvalidOperationException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    private static void initializeCreditCards(CreditCardsRepository repository) {
        repository.saveCreditCard(validCreditCardVisa);
        repository.saveCreditCard(validCreditCardNara);
        repository.saveCreditCard(validCreditCardAmex);
        repository.saveCreditCard(expiredCreditCardVisa);
        repository.saveCreditCard(expiredCreditCardNara);
        repository.saveCreditCard(expiredCreditCardAmex);
    }

    private static final CreditCard validCreditCardVisa = CreditCardCreator.createCreditCard(
            Brand.VISA, "1111222233331234", "aName1", "aLastname1",
            DateUtil.getCurrentMonth(), DateUtil.getNextYear());
    private static final CreditCard expiredCreditCardVisa = CreditCardCreator.createCreditCard(
            Brand.VISA, "1111222233335678", "aName2", "aLastname2",
            DateUtil.getCurrentMonth(), DateUtil.getPreviousYear());
    private static final CreditCard validCreditCardNara = CreditCardCreator.createCreditCard(
            Brand.NARA, "1111222233339012", "aName1", "aLastname1",
            DateUtil.getNextMonth(), DateUtil.getCurrentYear());
    private static final CreditCard expiredCreditCardNara = CreditCardCreator.createCreditCard(
            Brand.NARA, "1111222233333456", "aName2", "aLastname2",
            DateUtil.getPreviousMonth(), DateUtil.getCurrentYear());
    private static final CreditCard validCreditCardAmex = CreditCardCreator.createCreditCard(
            Brand.AMEX, "1111222233337890", "aName3", "aLastname3",
            DateUtil.getNextMonth(), DateUtil.getNextYear());
    private static final CreditCard validCreditCardAmex2 = CreditCardCreator.createCreditCard(
            Brand.AMEX, "1111222233337890", "aName3", "aLastname3",
            DateUtil.getNextMonth(), DateUtil.getNextYear());
    private static final CreditCard expiredCreditCardAmex = CreditCardCreator.createCreditCard(
            Brand.AMEX, "11112222333334321", "aName4", "aLastname4",
            DateUtil.getPreviousMonth(), DateUtil.getPreviousYear());
    private static final String INVALID_CREDIT_CARD_NUMBER = "9999999999999999";

    private static final Integer VALID_OPERATION_VALUE = 100;
    private static final Integer INVALID_OPERATION_VALUE = 2000;

}