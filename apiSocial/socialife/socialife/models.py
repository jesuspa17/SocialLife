from django.contrib.auth.models import User, AbstractUser
from django.db import models

# Create your models here.

class Usuario(models.Model):
    usuario = models.OneToOneField(User,blank=True)
    poblacion = models.CharField(max_length=100,blank=True)
    ciudad = models.CharField(max_length=100,blank=True)
    biografia = models.CharField(max_length=250,blank=True)
    ubicacion_actual = models.CharField(max_length=250,blank=True)
    foto = models.ImageField(max_length=255, upload_to='images', blank=True)

    def __str__(self):
        return self.usuario.username

class Categoria(models.Model):
    nombre_categoria = models.CharField(max_length=100)

    def __str__(self):
        return self.nombre_categoria

class Plan(models.Model):

    titulo = models.CharField(max_length=100)
    localizacion = models.CharField(max_length=200)
    ciudad = models.CharField(max_length=100)
    foto = models.ImageField(max_length=255, upload_to='images')
    poblacion = models.CharField(blank=True, max_length=100)
    fecha = models.DateField()
    categoria = models.ForeignKey(Categoria)
    descripcion = models.TextField(max_length=2000)
    coordenadas = models.CharField(max_length=100)
    usuario = models.ForeignKey(Usuario)

    def __str__(self):
        return self.titulo +" en "+self.ciudad

class EstaApuntadoA(models.Model):
    usuario = models.ForeignKey(Usuario)
    plan = models.ForeignKey(Plan)
    fecha = models.DateField()

    #Esto hace que el valor de la fila usuario-plan sólo se repita una vez.
    #Es decir, un usuario puede apuntarse al plan una sola vez.
    class Meta:
        unique_together = ('usuario', 'plan')


class Comentario(models.Model):
    usuario = models.ForeignKey(User)
    plan = models.ForeignKey(Plan)
    contenido = models.CharField(max_length=250)
    fecha = models.DateField()


class Mensaje(models.Model):
    usuario_emisor = models.ForeignKey(User,related_name='usuario_emisor')
    usuario_receptor = models.ForeignKey(User,related_name='usuario_receptor')
    contenido = models.CharField(max_length=250)
    fecha = models.DateField()


class Amistades(models.Model):
    usuario_principal = models.ForeignKey(Usuario, related_name="usuario_principal")
    usuario_amigo = models.ForeignKey(Usuario, related_name="usuario_amigo")
    fecha = models.DateField()

    class Meta:
        unique_together = ('usuario_principal','usuario_amigo')















