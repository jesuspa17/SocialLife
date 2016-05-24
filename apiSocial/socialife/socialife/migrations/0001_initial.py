# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Usuario',
            fields=[
                ('id', models.AutoField(primary_key=True, auto_created=True, serialize=False, verbose_name='ID')),
                ('usuario', models.CharField(max_length=100)),
                ('password', models.CharField(max_length=100)),
                ('nombre', models.CharField(max_length=100)),
                ('apellidos', models.CharField(max_length=100)),
                ('email', models.CharField(max_length=100)),
                ('poblacion', models.CharField(max_length=100)),
                ('ciudad', models.CharField(max_length=100)),
                ('biografia', models.CharField(max_length=200)),
                ('foto', models.ImageField(max_length=255, upload_to='images')),
            ],
        ),
    ]
