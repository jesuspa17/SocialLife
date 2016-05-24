# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('socialife', '0012_usuario'),
    ]

    operations = [
        migrations.CreateModel(
            name='Categoria',
            fields=[
                ('id', models.AutoField(serialize=False, primary_key=True, auto_created=True, verbose_name='ID')),
                ('nombre_categoria', models.CharField(max_length=100)),
            ],
        ),
        migrations.CreateModel(
            name='Plan',
            fields=[
                ('id', models.AutoField(serialize=False, primary_key=True, auto_created=True, verbose_name='ID')),
                ('titulo', models.CharField(max_length=100)),
                ('localizacion', models.CharField(max_length=200)),
                ('ciudad', models.CharField(max_length=100)),
                ('poblacion', models.CharField(max_length=100, blank=True)),
                ('fecha', models.DateField()),
                ('descripcion', models.CharField(max_length=2000)),
                ('coordenadas', models.CharField(max_length=100)),
                ('categoria', models.ForeignKey(to='socialife.Categoria')),
                ('usuario', models.ForeignKey(to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]
