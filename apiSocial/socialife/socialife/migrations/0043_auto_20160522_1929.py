# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0042_auto_20160522_1921'),
    ]

    operations = [
        migrations.CreateModel(
            name='Seguidos',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False, auto_created=True, verbose_name='ID')),
                ('fecha', models.DateField()),
                ('usuario_principal', models.ForeignKey(related_name='usuario_principal', to='socialife.Usuario')),
                ('usuario_seguido', models.ForeignKey(related_name='usuario_amigo', to='socialife.Usuario')),
            ],
        ),
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
        migrations.DeleteModel(
            name='Amistades',
        ),
        migrations.AlterUniqueTogether(
            name='seguidos',
            unique_together=set([('usuario_principal', 'usuario_seguido')]),
        ),
    ]
