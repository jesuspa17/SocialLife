# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0043_auto_20160522_1929'),
    ]

    operations = [
        migrations.CreateModel(
            name='Amistades',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('fecha', models.DateField()),
                ('usuario_amigo', models.ForeignKey(to='socialife.Usuario', related_name='usuario_amigo')),
                ('usuario_principal', models.ForeignKey(to='socialife.Usuario', related_name='usuario_principal')),
            ],
        ),
        migrations.AlterUniqueTogether(
            name='seguidos',
            unique_together=set([]),
        ),
        migrations.RemoveField(
            model_name='seguidos',
            name='usuario_principal',
        ),
        migrations.RemoveField(
            model_name='seguidos',
            name='usuario_seguido',
        ),
        migrations.DeleteModel(
            name='Seguidos',
        ),
        migrations.AlterUniqueTogether(
            name='amistades',
            unique_together=set([('usuario_principal', 'usuario_amigo')]),
        ),
    ]
