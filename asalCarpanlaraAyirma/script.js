function carpanlariBul() {
    let sayi = document.getElementById("sayi").value;
    
    let carpanlar=[];
    for(let i=1; i<=sayi; i++){
        if(sayi % i === 0){
            carpanlar.push(i);
        }
    }
    //console.log(carpanlar);

    document.getElementById("sonuc").innerHTML ="Girilen sayının çarpanları: " + carpanlar;
  }


  /*
  Asal sayılar, sadece iki pozitif tam sayı böleni olan doğal sayılardır. 
  Sadece kendisine ve 1 sayısına kalansız bölünebilen 1'den büyük tam sayılardır.
  */
  function asalCarpanlariBul() {
    let sayi = document.getElementById("sayi").value;

  let carpanlar=[];
  for(let i=1; i<=sayi; i++){
      if(sayi % i === 0){
          carpanlar.push(i);
      }
  }

    delete carpanlar[0]; // 1 asal çarpan değildir o yüzden sildik
    
    let asalCarpanlar=[];
    carpanlar.forEach(carpan => {
        
        /*
          carpanlar dizisi içindeki elemanlar tek tek gezilecek.
          her bir çarpan 2'den başlanarak girilen sayı değerinin sonuna kadar sayılara bölünecek.
          her bir kalansız bölme işlemi sonunda sayac 1 arttırılacak.
          her carpan kendine bölünür. bu yüzden sayac en az 1 kere artar.
          ama sayac 1 den fazla artmışsa bu sayının kendinden başka böleni var demektir ve asal sayı değildir denir.
        */
        let sayac=0;
        for(let i=2;i<=sayi; i++){
            if(carpan % i === 0){
                //console.log(carpan+" sayisi " +i + " sayisina bolunur" );
                sayac++;
            }
        }
        if(sayac === 1){
              asalCarpanlar.push(carpan);
      }
        
    });
    console.log(asalCarpanlar);

    document.getElementById("sonuc2").innerHTML ="Girilen sayının asal çarpanları: " + asalCarpanlar;
    

    //document.getElementById("sonuc2").innerHTML ="Girilen sayının asal çarpanları: " + "Bu kısım henüz tamamlanmadı  :(";

}