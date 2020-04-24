pragma solidity ^0.5.12;

contract SimpleSuma {
    //    DECLARA VARIABLE ENTERO SIN SIGNO
    uint suma;
    //  DECLARA CONSTRUCTOR
   
    function setSuma(uint valor) public {
     assert(valor > 0);
     suma = suma + valor;}
    function getSuma() public view returns(uint){ return suma; }
}