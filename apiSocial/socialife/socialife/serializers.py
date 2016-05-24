from django.contrib.auth.models import User, Group
from rest_framework import serializers
from socialife.socialife.models import Usuario, Categoria, Plan, EstaApuntadoA, Mensaje, Comentario, Amistades


class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = ('id','username','email','first_name','last_name')

class GroupSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Group
        fields = ('url', 'name')

class UsuarioAddSerializer(serializers.ModelSerializer):
    class Meta:
        model = Usuario
        fields = ('id','usuario','poblacion','ciudad','biografia','ubicacion_actual','foto')

class UsuarioSerializer(serializers.ModelSerializer):
     usuario = UserSerializer()
     class Meta:
        model = Usuario
        fields = ('id','usuario','poblacion','ciudad','biografia','ubicacion_actual','foto')

class CategoriaSerializer(serializers.ModelSerializer):

     class Meta:
         model = Categoria
         fields = ('id','nombre_categoria')

class PlanAddSerializer(serializers.ModelSerializer):
    class Meta:
        model = Plan
        fields = ('id', 'titulo','localizacion','ciudad','foto','poblacion','fecha','categoria','descripcion','coordenadas','usuario')

class PlanSerializer(serializers.ModelSerializer):
    usuario = UsuarioSerializer()
    categoria = CategoriaSerializer()

    class Meta:
        model = Plan
        fields = ('id', 'titulo','localizacion','ciudad','foto','poblacion','fecha','categoria','descripcion','coordenadas','usuario')


class EstaApuntadoAddSerializer(serializers.ModelSerializer):
    class Meta:
        model = EstaApuntadoA
        fields = ('id','usuario','plan','fecha')

class EstaApuntadoASerializer(serializers.ModelSerializer):
    usuario = UsuarioSerializer()
    plan = PlanSerializer()
    class Meta:
        model = EstaApuntadoA
        fields = ('id','usuario','plan','fecha')


class ComentarioSerializer(serializers.ModelSerializer):
    usuario = UserSerializer()
    plan = PlanSerializer()

    class Meta:
        model = Comentario
        fields = ('id','usuario','plan','contenido','fecha')

class MensajeSerializer(serializers.ModelSerializer):
    usuario_emisor = UsuarioSerializer()
    usuario_receptor = UsuarioSerializer()

    class Meta:
        model = Mensaje
        fields = ('usuario_emisor','usuario_receptor','contenido','fecha')


class AmistadesSerializer(serializers.ModelSerializer):
    usuario_amigo = UsuarioSerializer()
    class Meta:
        model = Amistades
        fields = ('id','usuario_principal','usuario_amigo','fecha')

class SeguidoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Amistades
        fields = ('id','usuario_principal','usuario_amigo','fecha')






