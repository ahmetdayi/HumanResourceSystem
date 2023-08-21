
# HumanResourceSystem
OBSS 2023 Java Yaz Stajı Yönergesi
Temmuz 2023 (v1.0)
1 OBSS 2023 Yaz Stajı Yönergesi
1. Giriş, Amaç ve Kapsam
Bu belge, 2023 yılı OBSS Java yaz stajı döneminde stajyerlerden yapılması beklenilen çalışma, uyulması gereken kurallar ve çalışma sonucunu değerlendirme ölçütlerini içermektedir.
2. Proje Çalışması
Yaz stajı boyunca stajyerlerin verilen sürede bu belgede belirtilen kriterlere uygun ve istenilen özellikleri sunan kapsamı sınırlandırılmış bir “İnsan Kaynakları İş İlanları ve Başvuruları Yönetimi Uygulaması” geliştirmesi beklenmektedir.

2.1. Kullanılacak Teknolojiler ve Kısıtlar
Proje kapsamında geliştirilecek uygulamanın web tabanlı olması, Java dili ve web teknolojileri (HTML, CSS, JavaScript, React, Vue, …) kullanılarak yazılması ve Java platformu üzerinde çalışabilir olma şartı aranmaktadır. Stajyerler, bu genel kısıt çerçevesinde istenen işlevselliği sağlayabilmek için tercih ettikleri ön yüz ya da sunucu tarafı teknolojileri, framework’leri ve kütüphaneleri kullanmakta özgürdür.

2.2. Çalışma Yöntemi
Stajyerler, çalışmalarını kendileri için ayrılan alanda bireysel olarak sürdürecektir. Çalışma sırasında internet kullanımı serbesttir. Ancak stajyerlerin proje geliştirme konusunda birbirleriyle yardımlaşması ya da mesai saatleri içinde veya dışında başka kişilerden danışmanlığın ötesinde bilfiil uygulama geliştirme desteği alması kural dışı olup tespit edilmesi halinde ilgili stajyerlerin çalışması yarışma kapsamı dışında tutulacaktır.

2.3. Uygulama İşlevleri
Geliştirilecek uygulamanın aşağıdaki işlevselliği sunması beklenmektedir:

2.3.1. Kullanıcı Profilleri
Uygulamayı 2 tip kullanıcı kullanabilir: IK Uzmanı ve Aday (İş Başvurusu Yapan Kişi)
Bu profillerin tanımlanması veya yönetimi için ekran ve fonksiyon beklenmemektedir. Ancak uygulama fonksiyonları bu profillerle ilişkilidir.

2.3.2. Authentication
IK Uzmanları sisteme LDAP Authentication ile login olmalıdır. Bunun için bir LDAP server kurulmalıdır. Kullanıcı tanımları doğrudan LDAP server üzerine yapılabilir. Uygulamada yeni kullanıcı tanımlama ve kullanıcı yönetimi için ekranlar olmasına gerek yoktur.
Adaylar ise sisteme LinkedIn profilleri ile login olmalıdır.

2.3.3. İlan Yönetimi
IK Uzmanları yeni ilan hazırlayabilir. Hazırladığı ilanı aktive edebilir. Aktif bir ilanı pasife çekebilir. İlan hazırlarken otomatik olarak aktif ve pasif olması için ileri tarih ve saatler set edebilir.

2 OBSS 2023 Yaz Stajı Yönergesi
Her bir ilanın kodu, başlığı, iş tanımı, adayda bulunması beklenen kişisel ve profesyonel özellikler, aktivasyon zamanı, kapanma zamanı olmalıdır.

2.3.4. İş Başvurusu Yapma
Adaylar LinkedIn profilleriyle iş ilanlarına başvuruda bulunabilir. İlanları görmek için login olmaya gerek yoktur. Ancak başvuru sırasında login olmak gerekir.
Aday başvuru yaptığında sistem daha sonra esnek aramalarda kullanabilmek için adayın detaylı LinkedIn profil bilgilerini sisteme alıp kaydetmelidir.

2.3.5. İş Başvurularını Görüntüleme (Aday)
Aday login olduğunda önceki başvurularının detaylarını ve statüsünü görebilmelidir.

2.3.6. İş Başvurularını Görüntüleme (IK Uzmanı)
IK Uzmanı ilanlar üzerinden o ilana yapılan başvuruları görüntüleyebilir. Bir ilana başvuran bir adayın başka hangi ilanlara başvurduğunu görüntüleyebilir. Adayın profil detaylarını görebilir. Başvuruyu işleme alabilir, kabul veya reddedebilir. Bir ilana yapılan başvuruları statüsüne göre filtreleyebilir. Başvuru statü değişikliklerinde sistem, başvuru sahibine uygun bir ifade ile mail yoluyla bilgilendirme mesajı gönderir.

2.3.7. Kara Listeye Alma
IK Uzmanı herhangi bir adayı, sebebini kayıt altına alarak kara listeye alabilir. Kara listeye alınan adaylar yeni iş başvurusu yapamaz. Eski başvuruları da reddedilir.

2.3.8. Free Format Text Bazlı Arama
IK Uzmanı başvuru sırasında topladığı profiller üzerinde google search benzeri free text arama yapabilir. Bu aramayı bir ilana başvuranlar içinde yapabileceği gibi herhangi bir başvurudan bağımsız olarak kaydedilen tüm profiller üzerinde de yapabilir. (Bunun için uygun bir NoSQL database, SOLR veya Elastic Search gibi alternatiflerin birinin veya bir kombinasyonunun kullanımı beklenmektedir)

2.4. Ekstra İşlevler
Ekstra işlevler, geliştirilecek uygulamada olması beklenen işlevlerin dışında olan ve değerlendirmeye olumlu katkısı olacak işlevlerdir. Bu işlevlerin değerlendirmeye katkısı olabilmesi için 2.3 altında verilen tüm işlevlerin eksiksiz olarak gerçeklenmesi gerekmektedir.

2.4.1. Başvuruları Uygunluk Sırasına Koyma
Sistem bir ilana yapılan başvuruların profillerinde yer alan bilgilerle ilanda yer alan şartlar arasındaki uyumu ölçümleyerek başvuruları uygunluk sırasına koyabilmelidir.

Backende docker implementasyonu yapılmıştır
