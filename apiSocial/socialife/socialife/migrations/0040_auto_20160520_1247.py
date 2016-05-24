# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0039_auto_20160517_1938'),
    ]

    operations = [
        migrations.AlterField(
            model_name='usuario',
            name='biografia',
            field=models.CharField(blank=True, max_length=250),
        ),
        migrations.AlterField(
            model_name='usuario',
            name='ciudad',
            field=models.CharField(blank=True, max_length=100),
        ),
        migrations.AlterField(
            model_name='usuario',
            name='foto',
            field=models.ImageField(blank=True, max_length=255, upload_to='images'),
        ),
        migrations.AlterField(
            model_name='usuario',
            name='poblacion',
            field=models.CharField(blank=True, max_length=100),
        ),
        migrations.AlterField(
            model_name='usuario',
            name='ubicacion_actual',
            field=models.CharField(blank=True, max_length=250),
        ),
        migrations.AlterField(
            model_name='usuario',
            name='usuario',
            field=models.OneToOneField(to=settings.AUTH_USER_MODEL, blank=True),
        ),
    ]
