# ELDAR Challenge - Diego Esquivel

Resolución del Challenge Java Backend

Aca deje anotaciones que me parecieron pertinentes para el entendimiento de mi solucion y algunas desiciones de modelado.

## Ejercicio 1

Para poder ver los resultados de este ejercicio lo que se tiene que hacer es ejecutar la clase Main.
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