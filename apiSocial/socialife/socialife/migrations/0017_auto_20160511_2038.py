# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('socialife', '0016_estaapuntadoa'),
    ]

    operations = [
        migrations.CreateModel(
            name='Comentario',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('contenido', models.CharField(max_length=250)),
                ('fecha', models.DateField()),
                ('plan', models.ForeignKey(to='socialife.Plan')),
                ('usuario', models.ForeignKey(to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='Mensaje',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('contenido', models.CharField(max_length=250)),
                ('fecha', models.DateField()),
                ('usuario_emisor', models.ForeignKey(to=settings.AUTH_USER_MODEL, related_name='usuario_emisor')),
                ('usuario_receptor', models.ForeignKey(to=settings.AUTH_USER_MODEL, related_name='usuario_receptor')),
            ],
        ),
        migrations.RenameField(
            model_name='estaapuntadoa',
            old_name='desde',
            new_name='fecha',
        ),
    ]
