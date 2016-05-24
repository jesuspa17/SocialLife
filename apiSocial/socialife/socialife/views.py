from datetime import timedelta, datetime, time
from itertools import chain

from django.contrib.auth.models import User,Group
from django.db.models import Prefetch
from django.shortcuts import render
from rest_framework.response import Response
from rest_framework import viewsets
from rest_framework.decorators import list_route
from socialife.socialife.models import Usuario, Categoria, Plan, EstaApuntadoA, Mensaje, Comentario, Amistades
from socialife.socialife.serializers import UserSerializer, GroupSerializer, UsuarioSerializer, CategoriaSerializer, \
    PlanSerializer, EstaApuntadoASerializer, MensajeSerializer, ComentarioSerializer, AmistadesSerializer, \
    UsuarioAddSerializer, PlanAddSerializer, EstaApuntadoAddSerializer, SeguidoSerializer


class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all().order_by('-date_joined')
    serializer_class = UserSerializer
    search_fields = ('id','username','email')
    filter_fields = ('id','email')

class GroupViewSet(viewsets.ModelViewSet):
    queryset = Group.objects.all()
    serializer_class = GroupSerializer


##################USUARIOS##########################

class UsuarioViewSet(viewsets.ModelViewSet):
    queryset = Usuario.objects.all().order_by('id')
    serializer_class = UsuarioSerializer
    search_fields = ('id')
    filter_fields = ('id',)


    def initial(self, request, *args, **kwargs):
        print(self.request.data)
        super(UsuarioViewSet, self).initial(request,args,kwargs)

    @list_route(methods=['GET'])
    def me(self, request):
          qs = Usuario.objects.filter(usuario=request.user).order_by('id')
          serializer = self.get_serializer(qs, many=True)
          return Response(serializer.data)


class UsuarioAddViewSet(viewsets.ModelViewSet):
    queryset = Usuario.objects.all().order_by('id')
    serializer_class = UsuarioAddSerializer
    search_fields = ('id')
    filter_fields = ('id',)

    def initial(self, request, *args, **kwargs):
        print(self.request.data)
        super(UsuarioAddViewSet, self).initial(request,args,kwargs)

    @list_route(methods=['GET'])
    def me(self, request):
          qs = Usuario.objects.filter(usuario=request.user).order_by('id')
          serializer = self.get_serializer(qs, many=True)
          return Response(serializer.data)


##################FIN USUARIOS##########################


class CategoriaViewSet(viewsets.ModelViewSet):
    queryset = Categoria.objects.all().order_by('id')
    serializer_class = CategoriaSerializer


class PlanViewSet(viewsets.ModelViewSet):
    queryset = Plan.objects.all().order_by('id')
    serializer_class = PlanSerializer
    search_fields = ('id','ciudad')
    filter_fields = ('id','ciudad','usuario__id','usuario__ubicacion_actual','fecha')

    #Obtiene los planes que estén actualmente vigentes creados por un usuario.
    @list_route(methods=['GET'])
    def planesVigentesDeUnUsuario(self, request):
          today = datetime.now().date()
          id_usuario= self.request.query_params.get('usuario__id',None)
          qs = Plan.objects.filter(fecha__gte=today,usuario__id=id_usuario).order_by('fecha')
          serializer = self.get_serializer(qs, many=True)
          return Response(serializer.data)

    #Obtiene los planes que sean para hoy, filtrando segun la ubicacion del usuario.
    @list_route(methods=['GET'])
    def planeshoy(self, request):
          today = datetime.now().date()
          # realizo el join entre tablas.
          ubicacion_actual_usuario = Usuario.objects.filter(usuario=request.user).values_list('ubicacion_actual')
          qs = Plan.objects.filter(fecha=today,ciudad=ubicacion_actual_usuario).order_by('fecha')
          serializer = self.get_serializer(qs, many=True)
          return Response(serializer.data)

    #Obtiene todos los planes que ha creado un usuario
    @list_route(methods=['GET'])
    def misplanes(self, request):
          today = datetime.now().date()
          # realizo el join entre tablas.
          qs = Plan.objects.filter(usuario__usuario_id=request.user.id).order_by('-fecha')
          serializer = self.get_serializer(qs, many=True)
          return Response(serializer.data)


    #Obtiene los planes del usuario logueado que esten a partir de hoy en adelante
    @list_route(methods=['GET'])
    def compruebaplanes(self, request):
          today = datetime.now().date()
          # realizo el join entre tablas.
          qs2 = Plan.objects.filter(fecha__gte=today,usuario__usuario_id=request.user.id).order_by('fecha')[:3]
          serializer = self.get_serializer(qs2, many=True)
          return Response(serializer.data)

    #Obtiene los planes que no sean para hoy, filtrando segun la ubicacion actual del usuario
    @list_route(methods=['GET'])
    def planesproximos(self, request):
          today = datetime.now().date()
          ubicacion_actual_usuario = Usuario.objects.filter(usuario=request.user).values_list('ubicacion_actual')
          qs = Plan.objects.filter(fecha__gt=today,ciudad=ubicacion_actual_usuario).order_by('id')
          serializer = self.get_serializer(qs, many=True)
          return Response(serializer.data)


class PlanAddViewSet(viewsets.ModelViewSet):
    queryset = Plan.objects.all().order_by('id')
    serializer_class = PlanAddSerializer
    search_fields = ('id','ciudad')
    filter_fields = ('id','ciudad','usuario')

    # #Obtiene los planes que sean para hoy
    # @list_route(methods=['GET'])
    # def misplaneshoy(self, request):
    #       today = datetime.now().date()
    #       # realizo el join entre tablas.
    #       qs = Plan.objects.filter(fecha=today,usuario__usuario_id=request.user.id).order_by('id')
    #       serializer = self.get_serializer(qs, many=True)
    #       return Response(serializer.data)


    # #Obtiene los planes que no sean para hoy.
    # @list_route(methods=['GET'])
    # def planesproximos(self, request):
    #       today = datetime.now().date()
    #       qs = Plan.objects.exclude(fecha=today,usuario__usuario_id=request.user.id).order_by('id')
    #       serializer = self.get_serializer(qs, many=True)
    #       return Response(serializer.data)

class EstaApuntadoAViewSet(viewsets.ModelViewSet):
    queryset = EstaApuntadoA.objects.filter(plan__fecha__gte=datetime.now().date()).order_by('-plan__fecha')
    serializer_class = EstaApuntadoASerializer
    search_fields = ('id','usuario__id')
    filter_fields = ('id','usuario__id','plan__id')

    #Obtiene los planes a los que el usuario actual está apuntado
    @list_route(methods=['GET'])
    def estoyapuntado(self, request):
          # realizo el join entre tablas.
          qs = EstaApuntadoA.objects.filter(usuario__usuario_id=request.user.id).order_by('-plan__fecha')
          serializer = self.get_serializer(qs, many=True)
          return Response(serializer.data)

class EstaApuntadoAddViewSet(viewsets.ModelViewSet):
    queryset = EstaApuntadoA.objects.all().order_by('id')
    serializer_class = EstaApuntadoAddSerializer
    search_fields = ('id','usuario__id')
    filter_fields = ('id','usuario__id','plan__id')


    #Obtiene los planes a los que el usuario actual está apuntado
    @list_route(methods=['GET'])
    def estoyapuntado(self, request):
          # realizo el join entre tablas.
          qs = EstaApuntadoA.objects.filter(usuario__usuario_id=request.user.id).order_by('id')
          serializer = self.get_serializer(qs, many=True)
          return Response(serializer.data)

class ComentarioViewSet(viewsets.ModelViewSet):
    queryset = Comentario.objects.all().order_by('id')
    serializer_class = ComentarioSerializer

class MensajeViewSet(viewsets.ModelViewSet):
    queryset = Mensaje.objects.all().order_by('id')
    serializer_class = MensajeSerializer

class AmistadesViewSet(viewsets.ModelViewSet):
    queryset = Amistades.objects.all().order_by('id')
    serializer_class = AmistadesSerializer

    @list_route(methods=['GET'])
    def obtenerseguidos(self, request):
          qs = Amistades.objects.filter(usuario_principal__usuario_id=request.user.id).order_by('id')
          serializer = self.get_serializer(qs, many=True)
          return Response(serializer.data)

class SeguidoViewSet(viewsets.ModelViewSet):
    queryset = Amistades.objects.all().order_by('id')
    serializer_class = SeguidoSerializer
    filter_fields = ('id','usuario_principal','usuario_amigo')










