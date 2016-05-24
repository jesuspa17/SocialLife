# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('socialife', '0030_estaapuntadoa'),
    ]

    operations = [
        migrations.CreateModel(
            name='Amistades',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('fecha', models.DateField()),
                ('usuario_amigo', models.ForeignKey(to=settings.AUTH_USER_MODEL, related_name='usuario_amigo')),
                ('usuario_principal', models.ForeignKey(to=settings.AUTH_USER_MODEL, related_name='usuario_principal')),
            ],
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
