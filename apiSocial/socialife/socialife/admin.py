from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from django.contrib.auth.models import User
from django.utils.translation import ugettext_lazy as _
from socialife.socialife.models import Usuario, Plan, Categoria, EstaApuntadoA, Comentario, Mensaje, Amistades


class UsuarioAdmin(admin.ModelAdmin):
    list_display = ('id','usuario','poblacion','ciudad','biografia','ubicacion_actual','foto')

class CategoriaAdmin(admin.ModelAdmin):
    list_display = ('nombre_categoria',)

class PlanAdmin(admin.ModelAdmin):
    list_display = ('titulo','localizacion','ciudad','foto','poblacion','fecha','categoria','descripcion','coordenadas','usuario')


class EstaApuntadoAAdmin(admin.ModelAdmin):
    list_display = ('id','usuario','plan','fecha')


class ComentarioAdmin(admin.ModelAdmin):
    list_display = ('usuario','plan','contenido','fecha')

class MensajeAdmin(admin.ModelAdmin):
    list_display = ('usuario_emisor','usuario_receptor','contenido','fecha')


class AmistadesAdmin(admin.ModelAdmin):
    list_display = ('id','usuario_principal','usuario_amigo','fecha')




admin.site.register(Usuario, UsuarioAdmin)
admin.site.register(Plan,PlanAdmin)
admin.site.register(Categoria,CategoriaAdmin)
admin.site.register(EstaApuntadoA,EstaApuntadoAAdmin)
admin.site.register(Comentario,ComentarioAdmin)
admin.site.register(Mensaje,MensajeAdmin)
admin.site.register(Amistades,AmistadesAdmin)