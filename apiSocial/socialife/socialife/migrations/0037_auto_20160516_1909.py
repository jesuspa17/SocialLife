# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('socialife', '0036_auto_20160516_1908'),
    ]

    operations = [
        migrations.CreateModel(
            name='Amistades',
            fields=[
                ('id', models.AutoField(serialize=False, auto_created=True, verbose_name='ID', primary_key=True)),
                ('fecha', models.DateField()),
                ('usuario_amigo', models.ForeignKey(related_name='usuario_amigo', to=settings.AUTH_USER_MODEL)),
                ('usuario_principal', models.ForeignKey(related_name='usuario_principal', to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='Categoria',
            fields=[
                ('id', models.AutoField(serialize=False, auto_created=True, verbose_name='ID', primary_key=True)),
                ('nombre_categoria', models.CharField(max_length=100)),
            ],
        ),
        migrations.CreateModel(
            name='Comentario',
            fields=[
                ('id', models.AutoField(serialize=False, auto_created=True, verbose_name='ID', primary_key=True)),
                ('contenido', models.CharField(max_length=250)),
                ('fecha', models.DateField()),
            ],
        ),
        migrations.CreateModel(
            name='EstaApuntadoA',
            fields=[
                ('id', models.AutoField(serialize=False, auto_created=True, verbose_name='ID', primary_key=True)),
                ('fecha', models.DateField()),
            ],
        ),
        migrations.CreateModel(
            name='Mensaje',
            fields=[
                ('id', models.AutoField(serialize=False, auto_created=True, verbose_name='ID', primary_key=True)),
                ('contenido', models.CharField(max_length=250)),
                ('fecha', models.DateField()),
                ('usuario_emisor', models.ForeignKey(related_name='usuario_emisor', to=settings.AUTH_USER_MODEL)),
                ('usuario_receptor', models.ForeignKey(related_name='usuario_receptor', to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='Plan',
            fields=[
                ('id', models.AutoField(serialize=False, auto_created=True, verbose_name='ID', primary_key=True)),
                ('titulo', models.CharField(max_length=100)),
                ('localizacion', models.CharField(max_length=200)),
                ('ciudad', models.CharField(max_length=100)),
                ('foto', models.ImageField(max_length=255, upload_to='images')),
                ('poblacion', models.CharField(max_length=100, blank=True)),
                ('fecha', models.DateField()),
                ('descripcion', models.CharField(max_length=2000)),
                ('coordenadas', models.CharField(max_length=100)),
                ('categoria', models.ForeignKey(to='socialife.Categoria')),
                ('usuario', models.ForeignKey(to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='Usuario',
            fields=[
                ('id', models.AutoField(serialize=False, auto_created=True, verbose_name='ID', primary_key=True)),
                ('poblacion', models.CharField(max_length=100)),
                ('ciudad', models.CharField(max_length=100)),
                ('biografia', models.CharField(max_length=250)),
                ('ubicacion_actual', models.CharField(max_length=250)),
                ('foto', models.ImageField(max_length=255, upload_to='images')),
                ('usuario', models.OneToOneField(to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.AddField(
            model_name='estaapuntadoa',
            name='plan',
            field=models.ForeignKey(to='socialife.Plan'),
        ),
        migrations.AddField(
            model_name='estaapuntadoa',
            name='usuario',
            field=models.ForeignKey(to=settings.AUTH_USER_MODEL),
        ),
        migrations.AddField(
            model_name='comentario',
            name='plan',
            field=models.ForeignKey(to='socialife.Plan'),
        ),
        migrations.AddField(
            model_name='comentario',
            name='usuario',
            field=models.ForeignKey(to=settings.AUTH_USER_MODEL),
        ),
        migrations.AlterUniqueTogether(
            name='estaapuntadoa',
            unique_together=set([('usuario', 'plan')]),
        ),
        migrations.AlterUniqueTogether(
            name='amistades',
            unique_together=set([('usuario_principal', 'usuario_amigo')]),
        ),
    ]
