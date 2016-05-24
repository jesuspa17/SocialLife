"""socialife URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.8/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import include, url
from django.contrib import admin
from rest_framework import routers
from socialife.socialife import views
from socialife import settings

router = routers.DefaultRouter()
router.register(r'users', views.UserViewSet)
router.register(r'groups', views.GroupViewSet)
router.register(r'usuarios', views.UsuarioViewSet)
router.register(r'usuariosadd', views.UsuarioAddViewSet)
router.register(r'categorias', views.CategoriaViewSet)
router.register(r'planes', views.PlanViewSet)
router.register(r'planesadd', views.PlanAddViewSet)
router.register(r'estaapuntado', views.EstaApuntadoAViewSet)
router.register(r'plan/apuntarse', views.EstaApuntadoAddViewSet)
router.register(r'plan/borrarse', views.EstaApuntadoAddViewSet)
router.register(r'comentarios', views.ComentarioViewSet)
router.register(r'mensajes', views.MensajeViewSet)
router.register(r'amistades', views.AmistadesViewSet)
router.register(r'seguido', views.SeguidoViewSet)


urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^rest-auth/', include('rest_auth.urls')),
    url(r'^rest-auth/registration/', include('rest_auth.registration.urls')),

    url(r'^api/', include(router.urls)),
    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework')),

    url(r'^static/(?P<path>.*)$', 'django.views.static.serve', {
        'document_root': settings.STATIC_ROOT
    }),
    url(r'^media/(?P<path>.*)$', 'django.views.static.serve', {
        'document_root': settings.MEDIA_ROOT
    }),
]
