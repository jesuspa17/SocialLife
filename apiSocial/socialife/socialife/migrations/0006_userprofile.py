# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('socialife', '0005_auto_20160508_0500'),
    ]

    operations = [
        migrations.CreateModel(
            name='UserProfile',
            fields=[
                ('id', models.AutoField(serialize=False, auto_created=True, verbose_name='ID', primary_key=True)),
                ('usuario', models.CharField(unique=True, max_length=100)),
                ('password', models.CharField(max_length=100)),
                ('nombre', models.CharField(max_length=100)),
                ('apellidos', models.CharField(max_length=100)),
                ('email', models.CharField(max_length=100)),
                ('poblacion', models.CharField(max_length=100)),
                ('ciudad', models.CharField(max_length=100)),
                ('biografia', models.CharField(max_length=200)),
                ('foto', models.ImageField(upload_to='images', max_length=255)),
                ('user', models.ForeignKey(unique=True, to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]
