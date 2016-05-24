# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0035_auto_20160512_2158'),
    ]

    operations = [
        migrations.AlterUniqueTogether(
            name='amistades',
            unique_together=set([]),
        ),
        migrations.RemoveField(
            model_name='amistades',
            name='usuario_amigo',
        ),
        migrations.RemoveField(
            model_name='amistades',
            name='usuario_principal',
        ),
        migrations.RemoveField(
            model_name='comentario',
            name='plan',
        ),
        migrations.RemoveField(
            model_name='comentario',
            name='usuario',
        ),
        migrations.AlterUniqueTogether(
            name='estaapuntadoa',
            unique_together=set([]),
        ),
        migrations.RemoveField(
            model_name='estaapuntadoa',
            name='plan',
        ),
        migrations.RemoveField(
            model_name='estaapuntadoa',
            name='usuario',
        ),
        migrations.RemoveField(
            model_name='mensaje',
            name='usuario_emisor',
        ),
        migrations.RemoveField(
            model_name='mensaje',
            name='usuario_receptor',
        ),
        migrations.RemoveField(
            model_name='plan',
            name='categoria',
        ),
        migrations.RemoveField(
            model_name='plan',
            name='usuario',
        ),
        migrations.RemoveField(
            model_name='usuario',
            name='usuario',
        ),
        migrations.DeleteModel(
            name='Amistades',
        ),
        migrations.DeleteModel(
            name='Categoria',
        ),
        migrations.DeleteModel(
            name='Comentario',
        ),
        migrations.DeleteModel(
            name='EstaApuntadoA',
        ),
        migrations.DeleteModel(
            name='Mensaje',
        ),
        migrations.DeleteModel(
            name='Plan',
        ),
        migrations.DeleteModel(
            name='Usuario',
        ),
    ]
