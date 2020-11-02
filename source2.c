# include < iostream >
using namespace std ;
int c ;
int a ;
int b ;
int main ( )
{
cout << "Introduceti numerele_a_si_b:" ;
cout << endl ;
cin >> a >> b ;

while ( a != b )
{
if ( a > b )
a = a - b ;
else
b = b - a ;
}
cout << "Cmmdc=" ;
cout << a ;
}