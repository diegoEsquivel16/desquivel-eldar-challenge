# ELDAR Challenge - Diego Esquivel

Resolución del Challenge Java Backend

Aca deje anotaciones que me parecieron pertinentes para el entendimiento de mi solucion y algunas desiciones de modelado.

## Ejercicio 1

Para poder ver los resultados de este ejercicio lo que se tiene que hacer es ejecutar la clase MainEjercicio1.
En la misma se va a mostrar por consola el paso a paso de los distintos puntos para este ejercicio, ejecutando distintos casos.

Me parece pertinente aclarar que si bien estoy mostrando por consola informacion sensible como datos de tarjetas de credito.
Esto lo hago asi ya que es un escenario ficticio donde no trabajo con informacion real. En un escenario real, el manejo 
de esta informacion tendria mucha mas precauciones, y mostrando solamente informacion acotada que no permita poner en peligro ninguna data sensible.

Para el primer challenge donde pedia devolver la informacion de una tarjeta de credito, me decidi por crear un repositorio 
de tarjetas para guardar las tarjetas que precargué antes de empezar a ejecutar los challenges para poder luego consultar
al mismo si la tarjeta existe y luego asi poder imprimir la informacion de la misma. 
Entiendo que esto probocó una complejidad extra al tener un objeto de Service con un Repository que solamente se termina usando
en este challenge. En caso de no ser necesario lo que haria seria borrar el repository y que el metodo "getAndPrintCreditCardInformation()"
solamente reciba una tarjeta de credito e imprima la data de la misma, sin necesidad de buscarla primero en el repository.

En general traté que el service tenga metodos lo mas simples posibles, con pocas responsabilidades. Entiendo que pudo haber
quedado como una clase larga, pero tiene varios metodos en los que solo devuelve strings que seran utilizados en el Main
para mostrarlos por pantalla.

Para el segundo challenge cree un metodo que recibe el valor de la operacion y hace la validacion de la misma, retornando
un string con el resultado de la validacion, aca no tiro exception ya que quiero saber qué resultado dio la validacion.
Existe otro metodo donde hago la validacion y si no pasa, tira exception. Este ultimo metodo lo uso en el ultimo challenge
donde habla de manejo de errores.


El valor maximo permitido para operar lo deje en una variable del service por simplicidad. Este campo podria definirse por
ambiente en caso de tener distintos ambientes posibles, entonces si llega a ser el caso este campo se puede asignar sacando
el valor de un archivo .properties en caso de que se use Spring.

Para el tercer challenge que pide validar tarjetas de credito me creé dos clases utils. CreditCardCreator para facilitarme la creacion
de instancias de tarjetas de credito. No utilice un factory por simplicidad ya que no lo vi necesario.
Y DateUtil para poder facilitarme la obtencion de fechas actuales o de años o meses que yo necesite, tanto actuales como
pasados y futuros.

Para el cuarto challenge lo que hice fue redefinir el metodo equals de la clase CreditCard para poder customizar cuando 
una tarjeta es igual a otra, esto va a pasar cuando los datos de los mismos sean iguales, comparando cada uno. De esta manera
evito el problema de que si hay dos instancias con la misma data, me diga que no son iguales las tarjetas.

Para el quinto challenge utilizo la validacion del limite de la operacion para hacer el manejo de errores, en caso de que
haya alguna validacion que no pase lo catcheo y muestro por pantalla el error que surgió. Como en este caso hay solo una 
validacion puede que parezca innecesario tener un enum con las posibles causas de operaciones invalidas, pero de esta manera
dejo abierta la posibilidad de agregar validaciones para esta operacion.

Tambien al hacer el calculo de la tasa de interes lo que hago es delegarle a cada marca que haga su propio calculo de la tasa
(haciendo uso tambien del util DateUtil) y luego de obtener la tasa que me devolvio la marca me aseguro de no salirme del rango
establecido (entre el 0.3% y el 5%) comparando el resultado obtenido con un valor maximo y minimo definido en otros campos.
Al igual que con el valor maximo permitido para operar están harcodeados por simplicidad pero los mismos pueden ser definidos
en un archivo .properties.


## Ejercicio 2

El servicio cuenta con una API de un solo metodo GET. 
Levanta localmente en el puerto 8080.

El mismo se comporta como el quinto challenge anterior, o sea que recibe un Brand y un valor para la operacion, y retorna
el valor de la tasa de operacion para dichos parametros:

        "/eldar_challenge/interest_rate?operation_value=<valor de la operacion>&brand=<marca de la tarjeta>"

El parametro 'operation_value' es un numero entero que acepta los valores entre 0 y 1000. Cualquier valor fuera de rango
se retorna un 404 BAD REQUEST con un mensaje indicando el error.

El parametro 'brand' es un string que indica la marca de la tarjeta a consultar. El mismo puede ser VISA, NARA y AMEX.
Cualquier otro valor que se reciba en la marca se retorna un 404 BAD REQUEST con un mensaje indicando el error.

Ambos parametros son necesarios, en caso de no recibir alguno de los dos se retorna un 404 BAD REQUEST.

En caso de que no haya error se recibe como respuesta un json como el siguiente:

        {
            "interest_rate": <resultado del calculo, numero tipo Double>
        }

A continuacion dejo algunos ejemplos de pegadas locales y sus distintos resultados:

- Request con una operacion de 100 con la tarjeta VISA:

        curl --location 'http://localhost:8080/eldar_challenge/interest_rate?operation_value=100&brand=VISA'
 
- Respuesta 200 OK:
        
        {
            "interest_rate": 480.0
        }

- Request con una opearcion de 100 con la tarjeta NARA: 
    
        curl --location 'http://localhost:8080/eldar_challenge/interest_rate?operation_value=100&brand=NARA'

- Respuesta 200 OK:

        {
            "interest_rate": 500.0
        }

- Request con una operacion de 100 con la tarjeta AMEX:

        curl --location 'http://localhost:8080/eldar_challenge/interest_rate?operation_value=100&brand=AMEX'

- Respuesta 200 OK:

        {
            "interest_rate": 50.0
        }

- Request sin pasarle la marca:
        
        curl --location 'http://localhost:8080/eldar_challenge/interest_rate?operation_value=100'

- Respuesta 400 BAD REQUEST:
        
        {
          "timestamp": 1715747653665,
          "status": 400,
          "error": "Bad Request",
          "message": "Required String parameter 'brand' is not present",
          "path": "/eldar_challenge/interest_rate"
        }

- Request sin pasarle el valor de la operacion:

        curl --location 'http://localhost:8080/eldar_challenge/interest_rate?brand=AMEX'

- Respuesta 400 BAD REQUEST:

        {
          "timestamp": 1715747765765,
          "status": 400,
          "error": "Bad Request",
          "message": "Required Integer parameter 'operation_value' is not present",
          "path": "/eldar_challenge/interest_rate"
        }

- Request con un valor de operacion fuera de rango:

        curl --location 'http://localhost:8080/eldar_challenge/interest_rate?operation_value=10000&brand=AMEX'

- Respuesta 400 BAD REQUEST:

        {
          "error": "The operation is invalid! Caused by: The import of the operation is out of range"
        }

- Request con una marca inexistente:

        curl --location 'http://localhost:8080/eldar_challenge/interest_rate?operation_value=10&brand=MASTER'
- Respuesta 400 BAD REQUEST:

        {
          "error": "Invalid Brand: MASTER"
        }

## Ejercicio 3

Consulta PL/SQL

Dada una tabla de empleados con 107 registros y la consulta:

        DECLARE
            CURSOR exp_cur IS
            SELECT first_name FROM employees; 
            TYPE nt_fName IS TABLE OF VARCHAR2(20);
            fname nt_fName;
        BEGIN
            OPEN exp_cur;
                FETCH exp_cur BULK COLLECT INTO fname LIMIT 10;
            CLOSE exp_cur;
            FOR idx IN 1 .. fname.COUNT
            LOOP
                DBMS_OUTPUT.PUT_LINE (idx||''||fname(idx) );
            END LOOP;
        END;

El resultado del mismo imprime 10 registros.

Esto se debe a que en la parte donde se hace el fetch
        
        FETCH exp_cur BULK COLLECT INTO fname LIMIT 10;

Se pone un limite de 10 registros al final en la consulta al cursor "exp_cur"
que arriba se declaro que utilizaba la tabla "employees"

## Ejercicio 4

Teniendo la siguiente funcion
        
        Create or Replace function Get_salary(P_Emp_id Number) Return Number As
        L_salary Number;
        Begin
        Select Salary into L_salary from Employees where employee_id = P_Emp_Id;
        End Get_salary;

Lo cierto es que no va a compilar porque le falta un "RETURN" luego de hacer el SELECT.
Podria arreglarse y quedaria de la siguiente manera:
    
    CREATE OR REPLACE function Get_salary
        (P_Emp_id Number) 
        RETURN Number 
    AS
        L_salary Number;
    BEGIN
        SELECT Salary INTO L_salary
            FROM Employees
            WHERE employee_id = P_Emp_Id;
        RETURN L_salary;
    END Get_salary;

De esta manera ahora la funcion retorna el valor del campo Salary dentro de la variable L_salary.

## Ejercicio 5

Para poder ejecutar este ejercicio se tiene que ejecutar la clase org.eldar.MainEjercicio5.

El mismo es bastante sencillo y directo.
Tengo un metodo que joinea los string de un array recibido por parametro. El main llama a este metodo con un
array propio antes definido con 3 Strings distintos

Lo que hace el metodo de join es:
- Primero creo un Stream con el array recibido como parametro, esto me permite ejecutar funciones de orden superior con
    mayor facilidad.
- Luego mapeo cada elemento del stream transformando cada valor al mismo string pero en formato Lower Case.
- Luego colecciono el array informando que tengo que fusionar cada valor, y que cada valor esté separado por un espacio " ".
- Finalmente termino teniendo como resultado un String con todos los campos que tenia el array, pasados a Lower Case y separados por un espacio.