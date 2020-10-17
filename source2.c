# include < iostream >
using namespace std;

int a , b ;
int main ( )
{
cout << "Introduceti numerele_a_si_b:" << endl ;
cin >> a >> b ;

while ( a != b )
{
if ( a > b )
a = a - b ;
else
b = b - a ;
}
cout << "Cmmdc=" << a ;
}